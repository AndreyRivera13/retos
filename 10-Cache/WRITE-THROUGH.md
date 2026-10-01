# Diseño escrito: Write-Through para el catálogo de precios

## 1. ¿Qué cambia en `actualizarPrecio` con Write-Through?

Con Cache-Aside, `actualizarPrecio` escribe en el origen y después **invalida** la entrada (`cache.remove(productoId)`). El caché queda vacío para esa clave hasta que alguien la lea y el miss la repueble.

Con Write-Through desaparece la invalidación: la misma operación escribe el nuevo valor en el origen **y en el caché** (`cache.put(productoId, new EntradaCache<>(nuevoPrecio))`), de forma síncrona. Es la escritura la que puebla el caché, no la siguiente lectura. Como consecuencia, `obtenerPrecio` casi nunca ve un miss después de una actualización.

El cambio también abre un caso que antes no existía: si se escribe bien en el origen pero falla el `put` al caché (o al revés), hay que decidir qué hacer para no dejar los dos desincronizados.

## 2. ¿El caché queda igual de fresco? ¿Qué cuesta?

Con Write-Through el caché queda fresco justo después de cada escritura que pasa por este método, mientras que con Cache-Aside queda vacío hasta la próxima lectura. Pero ninguna de las dos protege contra escrituras que van directo a la BD sin pasar por `actualizarPrecio` (otro servicio, un script, un ajuste manual). Para ese caso sigue haciendo falta el TTL, con cualquiera de las dos estrategias.

El costo de Write-Through está en la escritura:

- **Latencia:** cada escritura espera dos operaciones (origen + caché) en vez de una.
- **Complejidad:** hay que mantener consistentes las dos escrituras y decidir qué pasa si una falla (reintentar, revertir, dejar el dato fuera del caché).
- **Memoria:** se cachea todo lo que se escribe, aunque nadie lo vuelva a leer. Cache-Aside solo cachea lo que realmente se consulta.

## 3. Escenarios en mi proyecto (Entitlement / Bancolombia)

**Cache-Aside:** la consulta de permisos o roles de un usuario, que se lee en casi cada request y cambia pocas veces. Conviene Cache-Aside porque solo se cachean los usuarios que realmente consultan, y un valor viejo por unos segundos (hasta que venza el TTL) es tolerable. Write-Through no ayuda acá: obligaría a cachear permisos de usuarios que quizá no vuelvan a entrar, y encarecería cada escritura sin necesidad.

**Write-Through:** la revocación de un permiso o rol. Si el caché sigue diciendo que el usuario tiene acceso después de revocarlo, es un problema de seguridad, no solo un dato viejo. Con Write-Through la revocación actualiza origen y caché en la misma operación, así que el acceso se corta de inmediato. Aquí se justifica pagar más latencia en la escritura, porque la escritura es rara y la consistencia es lo importante. Con Cache-Aside habría una ventana, entre escribir y que alguien invalide o venza el TTL, en la que el acceso revocado todavía funciona.

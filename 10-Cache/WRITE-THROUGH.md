# Diseño escrito: Write-Through para el catálogo de precios

No es código, es para que documentes tu razonamiento (esto es justo lo que
te preguntaría el evaluador).

TODO — responde:

1. ¿Qué cambia exactamente en el método `actualizarPrecio` si usaras
   Write-Through en vez de Cache-Aside?
2. ¿El cache queda igual de "fresco" en todo momento con Write-Through?
   ¿Qué le cuesta eso a la escritura (latencia, complejidad)?
3. En tu proyecto real (Entitlement / Bancolombia), da UN escenario concreto
   donde usarías Cache-Aside y UNO donde usarías Write-Through, explicando
   por qué esa y no la otra.

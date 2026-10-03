# language: es
Característica: Reservar una cita médica
  Para no tener que ir al consultorio a pedir turno
  Como paciente
  Quiero reservar una cita con un doctor en un horario concreto

  Escenario: Reserva exitosa
    Dado que el doctor "dra-lopez" no tiene citas
    Cuando reservo una cita con "dra-lopez" para las "2026-10-05 09:00"
    Entonces la cita queda registrada con "dra-lopez" a las "2026-10-05 09:00"

  Escenario: Horario ya ocupado
    Dado que el doctor "dra-lopez" ya tiene una cita a las "2026-10-05 09:00"
    Cuando reservo una cita con "dra-lopez" para las "2026-10-05 09:00"
    Entonces el sistema me avisa "Horario ocupado"
    Y el doctor "dra-lopez" sigue teniendo una sola cita

  Escenario: Doctor inexistente
    Dado que no existe un doctor llamado "dr-fantasma"
    Cuando reservo una cita con "dr-fantasma" para las "2026-10-05 09:00"
    Entonces el sistema me avisa "Doctor no existe"
    Y no se registra ninguna cita

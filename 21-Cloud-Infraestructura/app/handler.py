import json


def handler(event, context):
    # TODO: reservar una cita (guardar en el almacenamiento) o consultarla.
    # Si eliges ECS o EC2, esto se reemplaza por tu servicio Spring Boot.
    return {"statusCode": 200, "body": json.dumps({"mensaje": "TODO"})}

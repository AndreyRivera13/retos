"""Priorización asistida por IA de hallazgos de checkov/tfsec.

Regla: la IA propone, una persona decide. Nada se cierra ni se aprueba solo.
"""
import json
import sys


def redactar(texto: str) -> str:
    # TODO: quitar secretos, ARNs con ids de cuenta, IPs y cualquier dato sensible
    # ANTES de enviar nada a un modelo.
    raise NotImplementedError


def armar_prompt(hallazgos: list[dict]) -> str:
    # TODO: instrucciones claras (rol, formato de salida, "no inventes", criterios de prioridad).
    raise NotImplementedError


def consultar_modelo(prompt: str) -> str:
    # TODO: llama al modelo que elijas. Sin API, devuelve una respuesta simulada y dilo en el README.
    raise NotImplementedError


def escribir_borrador(respuesta: str, ruta: str) -> None:
    # TODO: Markdown con prioridad, explicación y un campo "Decisión humana: pendiente".
    raise NotImplementedError


if __name__ == "__main__":
    entrada = sys.argv[1]
    with open(entrada, encoding="utf-8") as f:
        hallazgos = json.load(f)
    # TODO: orquesta los cuatro pasos de arriba

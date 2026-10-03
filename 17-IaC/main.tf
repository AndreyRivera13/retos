terraform {
  required_version = ">= 1.5.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.region
}

variable "region" {
  type    = string
  default = "us-east-1"
}

data "aws_caller_identity" "actual" {}

resource "aws_kms_key" "citas" {
  description         = "Cifrado de la tabla de citas"
  enable_key_rotation = true

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Sid       = "AdministracionDeLaCuenta"
      Effect    = "Allow"
      Principal = { AWS = "arn:aws:iam::${data.aws_caller_identity.actual.account_id}:root" }
      Action    = "kms:*"
      Resource  = "*"
    }]
  })
}

resource "aws_dynamodb_table" "citas" {
  name         = "citas"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "citaId"

  attribute {
    name = "citaId"
    type = "S"
  }

  point_in_time_recovery {
    enabled = true
  }

  server_side_encryption {
    enabled     = true
    kms_key_arn = aws_kms_key.citas.arn
  }
}

resource "aws_iam_role" "servicio_citas" {
  name = "servicio-citas"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect    = "Allow"
      Principal = { Service = "lambda.amazonaws.com" }
      Action    = "sts:AssumeRole"
    }]
  })
}

resource "aws_iam_role_policy" "acceso_tabla_citas" {
  name = "acceso-tabla-citas"
  role = aws_iam_role.servicio_citas.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "dynamodb:GetItem",
          "dynamodb:PutItem",
          "dynamodb:Query"
        ]
        Resource = aws_dynamodb_table.citas.arn
      },
      {
        Effect   = "Allow"
        Action   = ["kms:Decrypt", "kms:GenerateDataKey"]
        Resource = aws_kms_key.citas.arn
        Condition = {
          StringEquals = {
            "kms:ViaService" = "dynamodb.${var.region}.amazonaws.com"
          }
        }
      }
    ]
  })
}

output "tabla_citas_arn" {
  value = aws_dynamodb_table.citas.arn
}

output "rol_servicio_citas_arn" {
  value = aws_iam_role.servicio_citas.arn
}

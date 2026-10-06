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
  region                      = var.region
  access_key                  = var.usar_localstack ? "test" : null
  secret_key                  = var.usar_localstack ? "test" : null
  skip_credentials_validation = var.usar_localstack
  skip_metadata_api_check     = var.usar_localstack
  skip_requesting_account_id  = var.usar_localstack

  dynamic "endpoints" {
    for_each = var.usar_localstack ? [1] : []
    content {
      apigateway = "http://localhost:4566"
      dynamodb   = "http://localhost:4566"
      iam        = "http://localhost:4566"
      lambda     = "http://localhost:4566"
      s3         = "http://localhost:4566"
    }
  }
}

# TODO 1 - Almacenamiento: DynamoDB, RDS/Aurora o S3 (la tabla del reto 17 te sirve de base).

# TODO 2 - Cómputo: Lambda, ECS/Fargate o EC2. Con Lambda, `app/handler.py` es el código.
#          Su rol IAM solo puede hacer lo mínimo sobre el almacenamiento (reto 17).

# TODO 3 - Enrutamiento: API Gateway o balanceador de carga delante del cómputo.

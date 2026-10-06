variable "region" {
  type    = string
  default = "us-east-1"
}

variable "usar_localstack" {
  type        = bool
  default     = false
  description = "true para apuntar a LocalStack en vez de a una cuenta de AWS"
}

# TODO: declara una tabla DynamoDB (o RDS, tú eliges) mínima.
# resource "aws_dynamodb_table" "citas" {
#   ...
# }

# TODO: declara un rol IAM con permisos MÍNIMOS necesarios (nunca "*").
# resource "aws_iam_role" "..." { ... }
# resource "aws_iam_role_policy" "..." {
#   policy = jsonencode({
#     Version = "2012-10-17"
#     Statement = [{
#       Effect   = "Allow"
#       Action   = [ ]   # TODO: lista SOLO las acciones que realmente necesita
#       Resource = ""    # TODO
#     }]
#   })
# }

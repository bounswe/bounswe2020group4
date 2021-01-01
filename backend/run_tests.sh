# account service tests
docker build -t account-service-tests -f ./account_service/Dockerfile.test ./account_service/.
docker run --rm account-service-tests
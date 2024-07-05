### Dependencies / Setup

Java 17
Hashicorp vault
Docker
  Postgres, Kafka

1. run "docker compose up -d" to run the postgres db and pgadmin. Access pgadmin on http://localhost:5050 and create the below dbs
campaign, campaign-dev, payment, payment-dev
2. Install the hashicorp vault and create the following key values. These values might change for each service. I will try to give these values for each service in their respective readme.md files. The secret for this vault is ${TOKEN_VAULT} it should be stored as environment variable to access the vaults as other secrets.
   
{

  "keystore.secret": "somerndompwd",
  
  "myjwt.mysecret": "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437",
  
  "postgre.password": "root@123",
  
  "postgre.url": "jdbc:postgresql://localhost:5432/campaign-dev",
  
  "postgre.username": "forest",
  
  "splunk.index": "campaign_service_dev",
  
  "splunk.source": "http-campaign-event-logs",
  
  "splunk.token": "d54bd62a-3c6b-44a6-a19f-aea2ef1c8a93",
  
  "splunk.url": "http://localhost:8088"
  
}

Order of running the services:
  1. Campaign Service
  2. Payment Service
  3. Customer Service

# Campaign Service:
run the service directly into the IntelliJ run. It should build and run this Service. Spring security is enabled on this service. You need to first Generate the JWT token using https://localhost:5004/auth/generateToken and use this token for accessing all the endpoints. This token expires in 30 mins, you can change this expiration time in application.yml files. 

# Payment Service
Similar to Campaign service, you can run this service into IntelliJ directly. This service depends on Campaign Service for security tokens. If the Camapign service is down, it cannot authenticate. Only the create payments endpoint is accessible without JWT Authentication.

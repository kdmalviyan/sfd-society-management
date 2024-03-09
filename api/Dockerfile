FROM maven:3.9-amazoncorretto-21
USER 0

RUN mkdir -p /var/tmp/file-upload-temp-dir/

USER $CONTAINER_USER_ID

WORKDIR /user-manageement

COPY . .

CMD mvn clean spring-boot:run -Dspring-boot.run.profiles=prod
# Use the official MySQL image
FROM mysql/mysql-server:latest

# Set working directory inside the container
WORKDIR /tmp

# Copy SQL files to the container
COPY test_db/*.sql /tmp/
COPY test_db/*.dump /tmp/

# Copy main SQL file to MySQL initialization folder
COPY test_db/employees.sql /docker-entrypoint-initdb.d

# Set MySQL root password
ENV MYSQL_ROOT_PASSWORD example


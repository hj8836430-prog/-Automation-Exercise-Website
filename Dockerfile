# Base image
FROM maven:3.9.9-eclipse-temurin-17

# Install Chrome + dependencies
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    curl \
    gnupg \
    && wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list' \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && apt-get clean

# Set working directory
WORKDIR /app

# Copy project
COPY . .

# Build project
RUN mvn clean install -DskipTests

# Run tests
CMD ["mvn", "test"]

FROM maven:3.9.9-eclipse-temurin-17

RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    curl \
    unzip \
    --no-install-recommends \
    && wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/googlechrome-linux-keyring.gpg \
    && echo "deb [arch=amd64 signed-by=/usr/share/keyrings/googlechrome-linux-keyring.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y --no-install-recommends google-chrome-stable \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY . .

RUN mvn clean install -DskipTests

CMD ["sleep", "infinity"]


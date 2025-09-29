#!/bin/bash

set -e

echo "Building Cross Country Application..."

# Build backend
echo "Building backend..."
cd cross-country-backend
./mvnw clean package -DskipTests
docker build -t cross-country-api:latest .
cd ..

# Build frontend
echo "Building frontend..."
cd cross-country-frontend
npm ci
npm run build
docker build -t cross-country-frontend:latest .
cd ..

echo "Build completed successfully!"

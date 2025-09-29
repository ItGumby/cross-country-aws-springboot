#!/bin/bash

set -e

echo "Building Cross Country Application..."

# Build backend
echo "Building backend..."
cd backend
./mvnw clean package -DskipTests
docker build -t cross-country-api:latest .
cd ..

# Build frontend
echo "Building frontend..."
cd frontend
npm ci
npm run build
docker build -t frontend:latest .
cd ..

echo "Build completed successfully!"

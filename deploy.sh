#!/bin/bash

set -e

echo "Deploying Cross Country Application to Kubernetes..."

# Apply PostgreSQL configuration
echo "Deploying PostgreSQL..."
kubectl apply -f kubernetes-configs/postgres-deployment.yaml

# Wait for PostgreSQL to be ready
echo "Waiting for PostgreSQL to be ready..."
kubectl wait --for=condition=ready pod -l app=postgres --timeout=300s

# Apply backend configuration
echo "Deploying backend API..."
kubectl apply -f kubernetes-configs/backend-deployment.yaml

# Wait for backend to be ready
echo "Waiting for backend to be ready..."
kubectl wait --for=condition=ready pod -l app=cross-country-api --timeout=300s

# Apply frontend configuration
echo "Deploying frontend..."
kubectl apply -f kubernetes-configs/frontend-deployment.yaml

# Apply ingress and HPA
echo "Applying ingress and autoscaling..."
kubectl apply -f kubernetes-configs/ingress.yaml
kubectl apply -f kubernetes-configs/hpa.yaml

echo "Deployment completed successfully!"
echo "Check status with: kubectl get pods,services,ingress"

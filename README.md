# Cross Country Results Application

A web application to track and display cross-country team performance, race results, and athlete statistics.

## Architecture

- **Backend**: Spring Boot (Java 21) with PostgreSQL
- **Frontend**: Vue.js 3 with Tailwind CSS
- **Deployment**: Kubernetes on AWS EKS
- **Database**: PostgreSQL running on Kubernetes

## Prerequisites

- Java 21
- Node.js 18+
- Docker
- kubectl
- AWS CLI (for EKS deployment)

## Local Development

### Backend Setup

1. Start PostgreSQL locally:
```bash
docker run --name postgres -e POSTGRES_DB=cross_country_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres:15
```

2. Run the backend:
```bash
cd cross-country-backend
./mvnw spring-boot:run
```

### Frontend Setup

1. Install dependencies and run:
```bash
cd cross-country-frontend
npm install
npm run dev
```

The application will be available at http://localhost:3000

## Production Deployment

### 1. Create EKS Cluster

```bash
eksctl create cluster --name cross-country --region us-west-2 --nodes 3
aws eks update-kubeconfig --region us-west-2 --name cross-country
```

### 2. Build and Deploy

```bash
# Build Docker images
./build.sh

# Deploy to Kubernetes
./deploy.sh
```

### 3. Verify Deployment

```bash
kubectl get pods,services,ingress
kubectl logs -l app=cross-country-api
```

## Features Implemented

### Phase 1 - Backend Foundation ✅
- Spring Boot application with PostgreSQL
- Entity models (Team, Athlete, Race, Result)
- Repository layer with JPA
- Service layer with business logic
- REST API controllers

### Phase 2 - Frontend Foundation ✅
- Vue.js 3 application with TypeScript support
- Tailwind CSS for styling
- Vue Router for navigation
- Axios for API integration
- Component structure (Header, Footer, Views)

### Phase 3 - Containerization ✅
- Docker configurations for backend and frontend
- Multi-stage builds for optimization
- Nginx configuration for frontend serving

### Phase 4 - Kubernetes Deployment ✅
- PostgreSQL deployment with persistent storage
- Backend API deployment with environment configuration
- Frontend deployment with load balancing
- Ingress controller for external access
- Horizontal Pod Autoscaler for scaling

### Phase 5 - Build and Deployment Automation ✅
- Build scripts for Docker images
- Deployment scripts for Kubernetes
- Health checks and readiness probes

## API Endpoints

- `GET /api/teams` - Get all teams
- `GET /api/teams/{id}` - Get team by ID
- `GET /api/teams/search?name={name}` - Search teams
- `POST /api/teams` - Create team
- `PUT /api/teams/{id}` - Update team
- `DELETE /api/teams/{id}` - Delete team

## Database Schema

- **teams**: School information and metadata
- **athletes**: Runner profiles linked to teams
- **races**: Race events with date, location, division
- **results**: Individual race results linking athletes to races

## Monitoring and Scaling

- Horizontal Pod Autoscaler configured for CPU/memory thresholds
- Health checks for application availability
- Resource limits and requests defined
- LoadBalancer services for external access

## Next Steps

To complete the implementation:

1. **Add remaining controllers** (Athlete, Race, Result)
2. **Implement authentication** with Spring Security and JWT
3. **Add data import functionality** for CSV files
4. **Create statistics and charts** with Chart.js
5. **Add comprehensive testing** (unit and integration tests)
6. **Implement monitoring** with Prometheus and Grafana

## Troubleshooting

### Common Issues

1. **PostgreSQL connection issues**: Check if PostgreSQL pod is running
2. **Image pull errors**: Ensure Docker images are built and tagged correctly
3. **Service discovery**: Verify service names match in configurations

### Useful Commands

```bash
# Check pod logs
kubectl logs -l app=cross-country-api

# Port forward for local testing
kubectl port-forward service/frontend-service 8080:80

# Scale deployments manually
kubectl scale deployment cross-country-api --replicas=5
```

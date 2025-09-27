# Cross-Country States Website Implementation Guide

## Prerequisites

### Development Environment Setup
1. **Java Development Kit (JDK) 21**
   - Download and install OpenJDK 21
   - Set JAVA_HOME environment variable

2. **Node.js and npm**
   - Install Node.js 18+ and npm
   - Verify installation: `node --version` and `npm --version`

3. **Database Setup**
   - Install PostgreSQL locally for development
   - For production: PostgreSQL will run on Kubernetes
   - Create development database: `cross_country_db`
   - Create user with appropriate permissions

4. **Development Tools**
   - IDE: IntelliJ IDEA or VS Code
   - Git for version control
   - Postman for API testing

## Phase 1: Backend Foundation (Week 1)

### Step 1: Spring Boot Project Setup
1. **Initialize Spring Boot Project**
   ```bash
   # Using Spring Initializr or IDE
   # Dependencies: Web, JPA, PostgreSQL, Security, Validation
   ```

2. **Configure Application Properties**
   ```yaml
   # application.yml (development)
   spring:
     profiles:
       active: dev
   ---
   spring:
     profiles: dev
     datasource:
       url: jdbc:postgresql://localhost:5432/cross_country_db
       username: your_username
       password: your_password
     jpa:
       hibernate:
         ddl-auto: create-drop
       show-sql: true
   ---
   spring:
     profiles: kubernetes
     datasource:
       url: jdbc:postgresql://postgres-service:5432/cross_country_db
       username: ${SPRING_DATASOURCE_USERNAME}
       password: ${SPRING_DATASOURCE_PASSWORD}
     jpa:
       hibernate:
         ddl-auto: validate
       show-sql: false
   ```

### Step 2: Database Schema Implementation
1. **Create Entity Classes**
   - `Team.java` - Team information
   - `Athlete.java` - Runner details
   - `Race.java` - Race events and metadata
   - `Result.java` - Individual race results

2. **Define Relationships**
   ```java
   @Entity
   public class Team {
       @Id @GeneratedValue
       private Long id;
       private String schoolName;
       private String schoolSize; // 5A-1A
       private String conference;
       
       @OneToMany(mappedBy = "team")
       private List<Athlete> athletes;
   }
   ```

3. **Create Database Tables**
   - Run application to auto-generate schema
   - Verify tables created correctly

### Step 3: Repository Layer
1. **Create Repository Interfaces**
   ```java
   @Repository
   public interface TeamRepository extends JpaRepository<Team, Long> {
       List<Team> findBySchoolNameContaining(String name);
   }
   ```

2. **Implement Custom Queries**
   - Search functionality for teams and athletes
   - Race result filtering and sorting

### Step 4: Service Layer
1. **Create Service Classes**
   ```java
   @Service
   public class TeamService {
       @Autowired
       private TeamRepository teamRepository;
       
       public List<Team> getAllTeams() {
           return teamRepository.findAll();
       }
   }
   ```

2. **Implement Business Logic**
   - Team management operations
   - Race result calculations
   - Statistics generation

### Step 5: REST API Controllers
1. **Create Controller Classes**
   ```java
   @RestController
   @RequestMapping("/api/teams")
   public class TeamController {
       @GetMapping
       public ResponseEntity<List<Team>> getAllTeams() {
           return ResponseEntity.ok(teamService.getAllTeams());
       }
   }
   ```

2. **Define API Endpoints**
   - `/api/teams` - Team operations
   - `/api/athletes` - Athlete management
   - `/api/races` - Race data
   - `/api/results` - Race results

## Phase 2: Frontend Foundation (Week 2)

### Step 6: Vue.js Project Setup
1. **Initialize Vue Project**
   ```bash
   npm create vue@latest cross-country-frontend
   cd cross-country-frontend
   npm install
   ```

2. **Install Dependencies**
   ```bash
   npm install tailwindcss @tailwindcss/forms
   npm install chart.js vue-chartjs
   npm install axios
   npm install vue-router@4
   ```

3. **Configure Tailwind CSS**
   ```bash
   npx tailwindcss init -p
   ```

### Step 7: Component Structure
1. **Create Base Components**
   - `Header.vue` - Navigation header
   - `Footer.vue` - Site footer
   - `Layout.vue` - Main layout wrapper

2. **Create Feature Components**
   - `TeamCard.vue` - Team display card
   - `AthleteProfile.vue` - Athlete information
   - `RaceResults.vue` - Results table
   - `StatsChart.vue` - Performance charts

### Step 8: Routing Setup
1. **Configure Vue Router**
   ```javascript
   const routes = [
     { path: '/', component: Home },
     { path: '/teams/:id', component: TeamResults },
     { path: '/athletes/:id', component: IndividualResults },
     { path: '/admin', component: AdminDashboard }
   ]
   ```

2. **Implement Navigation**
   - Main navigation menu
   - Breadcrumb navigation
   - Mobile-responsive menu

### Step 9: API Integration
1. **Create API Service**
   ```javascript
   // api.js
   import axios from 'axios'
   
   const api = axios.create({
     baseURL: 'http://localhost:8080/api'
   })
   
   export const teamService = {
     getAllTeams: () => api.get('/teams'),
     getTeam: (id) => api.get(`/teams/${id}`)
   }
   ```

2. **Implement Data Fetching**
   - Use Composition API for data management
   - Handle loading states and errors
   - Implement caching strategies

## Phase 3: Core Features (Week 3)

### Step 10: Home Dashboard
1. **Create Dashboard Layout**
   - Recent races section
   - Search functionality
   - Quick navigation cards

2. **Implement Search Features**
   ```vue
   <template>
     <div class="search-container">
       <input v-model="searchTerm" 
              @input="performSearch" 
              placeholder="Search runners or teams..." />
       <div v-if="searchResults.length" class="results">
         <!-- Display search results -->
       </div>
     </div>
   </template>
   ```

### Step 11: Individual Results Page
1. **Create Athlete Profile Component**
   - Display athlete information
   - Show personal best times
   - Race history table

2. **Implement Performance Charts**
   ```javascript
   // Using Chart.js
   const chartData = {
     labels: raceData.map(r => r.date),
     datasets: [{
       label: 'Race Times',
       data: raceData.map(r => r.time),
       borderColor: 'rgb(75, 192, 192)'
     }]
   }
   ```

3. **Add 3D Distribution Chart**
   - Show athlete performance vs division
   - Highlight individual among bell curve
   - Interactive chart features

### Step 12: Team Results Page
1. **Create Team Dashboard**
   - Team information header
   - Results table by race date
   - Team statistics summary

2. **Implement Team Performance Charts**
   - Team progression over season
   - Comparison with other teams
   - Individual contributions

### Step 13: Admin Interface
1. **Create Admin Dashboard**
   - Race management interface
   - Data import functionality
   - User management

2. **Implement Data Import**
   ```javascript
   const importRaceResults = async (csvFile) => {
     const formData = new FormData()
     formData.append('file', csvFile)
     
     try {
       const response = await api.post('/admin/import', formData)
       // Handle success
     } catch (error) {
       // Handle error
     }
   }
   ```

## Phase 4: Authentication & Security (Week 4)

### Step 14: Spring Security Configuration
1. **Configure JWT Authentication**
   ```java
   @Configuration
   @EnableWebSecurity
   public class SecurityConfig {
       @Bean
       public SecurityFilterChain filterChain(HttpSecurity http) {
           return http
               .authorizeHttpRequests(auth -> auth
                   .requestMatchers("/api/public/**").permitAll()
                   .requestMatchers("/api/admin/**").hasRole("ADMIN")
                   .anyRequest().authenticated())
               .build();
       }
   }
   ```

2. **Implement User Management**
   - User entity and repository
   - Authentication service
   - Password encryption

### Step 15: Frontend Authentication
1. **Create Authentication Store**
   ```javascript
   // Using Pinia or Vuex
   export const useAuthStore = defineStore('auth', {
     state: () => ({
       user: null,
       token: localStorage.getItem('token')
     }),
     actions: {
       async login(credentials) {
         // Login implementation
       }
     }
   })
   ```

2. **Implement Route Guards**
   - Protect admin routes
   - Handle authentication redirects
   - Manage user sessions

## Phase 5: Advanced Features (Week 5)

### Step 16: Statistics and Analytics
1. **Implement Statistical Calculations**
   ```java
   @Service
   public class StatisticsService {
       public QuintileData calculateQuintiles(List<Result> results) {
           // Calculate 20th, 40th, 60th, 80th percentiles
       }
       
       public DistributionData getTimeDistribution(Long raceId) {
           // Generate bell curve data
       }
   }
   ```

2. **Create Advanced Charts**
   - Performance trend analysis
   - Comparative statistics
   - Interactive data visualization

### Step 17: Data Import and Export
1. **CSV Import Functionality**
   ```java
   @PostMapping("/admin/import")
   public ResponseEntity<?> importRaceResults(@RequestParam("file") MultipartFile file) {
       try {
           importService.processCSV(file);
           return ResponseEntity.ok("Import successful");
       } catch (Exception e) {
           return ResponseEntity.badRequest().body("Import failed");
       }
   }
   ```

2. **Export Features**
   - PDF generation for results
   - CSV export functionality
   - Email integration

### Step 18: Performance Optimization
1. **Database Optimization**
   - Add appropriate indexes
   - Optimize queries
   - Implement caching

2. **Frontend Optimization**
   - Lazy loading components
   - Image optimization
   - Bundle size optimization

## Phase 6: Testing and Deployment (Week 6)

### Step 19: Testing Implementation
1. **Backend Testing**
   ```java
   @SpringBootTest
   class TeamServiceTest {
       @Test
       void shouldReturnAllTeams() {
           // Test implementation
       }
   }
   ```

2. **Frontend Testing**
   ```javascript
   // Using Vitest
   describe('TeamCard', () => {
     it('displays team information correctly', () => {
       // Test implementation
     })
   })
   ```

### Step 19.5: Containerization
1. **Create Backend Dockerfile**
   ```dockerfile
   # Dockerfile (backend)
   FROM openjdk:21-jre-slim
   COPY target/cross-country-api-*.jar app.jar
   EXPOSE 8080
   ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=kubernetes"]
   ```

2. **Create Frontend Dockerfile**
   ```dockerfile
   # Dockerfile (frontend)
   FROM node:18-alpine AS build
   WORKDIR /app
   COPY package*.json ./
   RUN npm ci
   COPY . .
   RUN npm run build
   
   FROM nginx:alpine
   COPY --from=build /app/dist /usr/share/nginx/html
   COPY nginx.conf /etc/nginx/nginx.conf
   EXPOSE 80
   ```

3. **Build and Push Images**
   ```bash
   # Build backend
   ./mvnw clean package
   docker build -t your-registry/cross-country-api:latest .
   docker push your-registry/cross-country-api:latest
   
   # Build frontend
   docker build -t your-registry/cross-country-frontend:latest .
   docker push your-registry/cross-country-frontend:latest
   ```

### Step 20: Kubernetes Deployment Setup

1. **Configure EKS Cluster**
   ```bash
   # Create EKS cluster
   eksctl create cluster --name cross-country --region us-west-2 --nodes 3
   
   # Configure kubectl
   aws eks update-kubeconfig --region us-west-2 --name cross-country
   ```

2. **Deploy PostgreSQL on Kubernetes**
   ```yaml
   # postgres-deployment.yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: postgres
   spec:
     replicas: 1
     selector:
       matchLabels:
         app: postgres
     template:
       metadata:
         labels:
           app: postgres
       spec:
         containers:
         - name: postgres
           image: postgres:15
           env:
           - name: POSTGRES_DB
             value: cross_country_db
           - name: POSTGRES_USER
             valueFrom:
               secretKeyRef:
                 name: postgres-secret
                 key: username
           - name: POSTGRES_PASSWORD
             valueFrom:
               secretKeyRef:
                 name: postgres-secret
                 key: password
           ports:
           - containerPort: 5432
           volumeMounts:
           - name: postgres-storage
             mountPath: /var/lib/postgresql/data
         volumes:
         - name: postgres-storage
           persistentVolumeClaim:
             claimName: postgres-pvc
   ---
   apiVersion: v1
   kind: Service
   metadata:
     name: postgres-service
   spec:
     selector:
       app: postgres
     ports:
     - port: 5432
       targetPort: 5432
   ```

3. **Create Persistent Volume for PostgreSQL**
   ```yaml
   # postgres-pvc.yaml
   apiVersion: v1
   kind: PersistentVolumeClaim
   metadata:
     name: postgres-pvc
   spec:
     accessModes:
     - ReadWriteOnce
     resources:
       requests:
         storage: 20Gi
     storageClassName: gp2
   ```

4. **Deploy Backend Application**
   ```yaml
   # backend-deployment.yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: cross-country-api
   spec:
     replicas: 3
     selector:
       matchLabels:
         app: cross-country-api
     template:
       metadata:
         labels:
           app: cross-country-api
       spec:
         containers:
         - name: api
           image: cross-country-api:latest
           ports:
           - containerPort: 8080
           env:
           - name: SPRING_DATASOURCE_URL
             value: jdbc:postgresql://postgres-service:5432/cross_country_db
           - name: SPRING_DATASOURCE_USERNAME
             valueFrom:
               secretKeyRef:
                 name: postgres-secret
                 key: username
           - name: SPRING_DATASOURCE_PASSWORD
             valueFrom:
               secretKeyRef:
                 name: postgres-secret
                 key: password
   ---
   apiVersion: v1
   kind: Service
   metadata:
     name: api-service
   spec:
     selector:
       app: cross-country-api
     ports:
     - port: 80
       targetPort: 8080
     type: LoadBalancer
   ```

5. **Deploy Frontend Application**
   ```yaml
   # frontend-deployment.yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: cross-country-frontend
   spec:
     replicas: 2
     selector:
       matchLabels:
         app: cross-country-frontend
     template:
       metadata:
         labels:
           app: cross-country-frontend
       spec:
         containers:
         - name: frontend
           image: nginx:alpine
           ports:
           - containerPort: 80
           volumeMounts:
           - name: frontend-files
             mountPath: /usr/share/nginx/html
         volumes:
         - name: frontend-files
           configMap:
             name: frontend-config
   ---
   apiVersion: v1
   kind: Service
   metadata:
     name: frontend-service
   spec:
     selector:
       app: cross-country-frontend
     ports:
     - port: 80
       targetPort: 80
     type: LoadBalancer
   ```

6. **Configure AWS Services Integration**
   ```yaml
   # aws-config.yaml
   apiVersion: v1
   kind: ConfigMap
   metadata:
     name: aws-config
   data:
     AWS_REGION: us-west-2
     S3_BUCKET: cross-country-files
   ---
   apiVersion: v1
   kind: Secret
   metadata:
     name: aws-credentials
   type: Opaque
   data:
     access-key-id: <base64-encoded-access-key>
     secret-access-key: <base64-encoded-secret-key>
   ```

7. **Deploy All Resources**
   ```bash
   # Create secrets
   kubectl create secret generic postgres-secret \
     --from-literal=username=postgres \
     --from-literal=password=your-password
   
   # Apply all configurations
   kubectl apply -f postgres-pvc.yaml
   kubectl apply -f postgres-deployment.yaml
   kubectl apply -f backend-deployment.yaml
   kubectl apply -f frontend-deployment.yaml
   kubectl apply -f aws-config.yaml
   
   # Verify deployments
   kubectl get pods
   kubectl get services
   ```

8. **Configure Ingress Controller**
   ```yaml
   # ingress.yaml
   apiVersion: networking.k8s.io/v1
   kind: Ingress
   metadata:
     name: cross-country-ingress
     annotations:
       kubernetes.io/ingress.class: alb
       alb.ingress.kubernetes.io/scheme: internet-facing
       alb.ingress.kubernetes.io/target-type: ip
   spec:
     rules:
     - host: api.cross-country.com
       http:
         paths:
         - path: /
           pathType: Prefix
           backend:
             service:
               name: api-service
               port:
                 number: 80
     - host: cross-country.com
       http:
         paths:
         - path: /
           pathType: Prefix
           backend:
             service:
               name: frontend-service
               port:
                 number: 80
   ```

9. **Install Service Mesh (Optional)**
   ```bash
   # Install Istio
   curl -L https://istio.io/downloadIstio | sh -
   istioctl install --set values.defaultRevision=default
   
   # Enable sidecar injection
   kubectl label namespace default istio-injection=enabled
   ```

10. **Configure Monitoring**
    ```yaml
    # monitoring.yaml
    apiVersion: v1
    kind: ConfigMap
    metadata:
      name: prometheus-config
    data:
      prometheus.yml: |
        global:
          scrape_interval: 15s
        scrape_configs:
        - job_name: 'kubernetes-pods'
          kubernetes_sd_configs:
          - role: pod
    ---
    apiVersion: apps/v1
    kind: Deployment
    metadata:
      name: prometheus
    spec:
      replicas: 1
      selector:
        matchLabels:
          app: prometheus
      template:
        metadata:
          labels:
            app: prometheus
        spec:
          containers:
          - name: prometheus
            image: prom/prometheus:latest
            ports:
            - containerPort: 9090
            volumeMounts:
            - name: config
              mountPath: /etc/prometheus
          volumes:
          - name: config
            configMap:
              name: prometheus-config
    ```

11. **Configure Auto-scaling**
    ```yaml
    # hpa.yaml
    apiVersion: autoscaling/v2
    kind: HorizontalPodAutoscaler
    metadata:
      name: api-hpa
    spec:
      scaleTargetRef:
        apiVersion: apps/v1
        kind: Deployment
        name: cross-country-api
      minReplicas: 2
      maxReplicas: 10
      metrics:
      - type: Resource
        resource:
          name: cpu
          target:
            type: Utilization
            averageUtilization: 70
    ```

## Phase 7: Final Testing and Launch

### Step 21: Integration Testing
1. **End-to-End Testing**
   - Test complete user workflows
   - Verify data integrity
   - Performance testing

2. **User Acceptance Testing**
   - Coach/admin testing
   - Parent/student testing
   - Feedback collection

### Step 22: Production Deployment
1. **Final Deployment**
   - Deploy to production environment
   - Configure monitoring and logging
   - Set up backup procedures

2. **Launch Preparation**
   - Create user documentation
   - Train administrators
   - Plan rollout strategy

## Maintenance and Monitoring

### Ongoing Tasks
1. **Regular Maintenance**
   - Database backups
   - Security updates
   - Performance monitoring

2. **Feature Updates**
   - User feedback implementation
   - New feature development
   - Bug fixes and improvements

## Success Metrics Tracking
- Monitor page load times (target: <3 seconds)
- Track user engagement metrics
- Measure data accuracy and completeness
- Collect user satisfaction feedback

## Troubleshooting Guide
- Common deployment issues
- Database connection problems
- Authentication troubleshooting
- Performance optimization tips

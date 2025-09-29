<template>
  <div class="container mx-auto px-4 py-8">
    <div v-if="loading" class="text-center">Loading...</div>
    <div v-else-if="error" class="text-red-600 text-center">{{ error }}</div>
    <div v-else-if="team">
      <h1 class="text-3xl font-bold mb-6">{{ team.name }}</h1>
      <div class="bg-white rounded-lg shadow p-6 mb-6">
        <h2 class="text-xl font-semibold mb-4">Team Information</h2>
        <p><strong>School:</strong> {{ team.school }}</p>
        <p><strong>Division:</strong> {{ team.division }}</p>
        <p><strong>Coach:</strong> {{ team.coach }}</p>
      </div>
      <div class="bg-white rounded-lg shadow p-6">
        <h2 class="text-xl font-semibold mb-4">Athletes</h2>
        <div v-if="team.athletes && team.athletes.length > 0" class="grid gap-4">
          <div v-for="athlete in team.athletes" :key="athlete.id" 
               class="border rounded p-4 hover:bg-gray-50">
            <router-link :to="`/athletes/${athlete.id}`" class="text-blue-600 hover:underline">
              {{ athlete.firstName }} {{ athlete.lastName }}
            </router-link>
            <p class="text-gray-600">{{ athlete.grade }}th Grade</p>
          </div>
        </div>
        <p v-else class="text-gray-500">No athletes found for this team.</p>
      </div>
    </div>
  </div>
</template>

<script>
import { teamService } from '../services/api'

export default {
  name: 'TeamDetail',
  props: ['id'],
  data() {
    return {
      team: null,
      loading: true,
      error: null
    }
  },
  async mounted() {
    await this.fetchTeam()
  },
  methods: {
    async fetchTeam() {
      try {
        this.loading = true
        const response = await teamService.getTeam(this.id)
        this.team = response.data
      } catch (error) {
        this.error = 'Failed to load team details'
        console.error('Error fetching team:', error)
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

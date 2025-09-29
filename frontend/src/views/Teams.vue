<template>
  <div class="space-y-6">
    <div class="flex justify-between items-center">
      <h1 class="text-3xl font-bold text-gray-900">Teams</h1>
    </div>

    <div v-if="loading" class="text-center py-8">Loading teams...</div>
    
    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div
        v-for="team in teams"
        :key="team.id"
        class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow cursor-pointer"
        @click="$router.push(`/teams/${team.id}`)"
      >
        <h3 class="text-xl font-semibold text-gray-900 mb-2">{{ team.schoolName }}</h3>
        <div class="space-y-1 text-sm text-gray-600">
          <p v-if="team.schoolSize">School Size: {{ team.schoolSize }}</p>
          <p v-if="team.conference">Conference: {{ team.conference }}</p>
        </div>
      </div>
    </div>

    <div v-if="!loading && teams.length === 0" class="text-center py-8 text-gray-500">
      No teams found
    </div>
  </div>
</template>

<script>
import { teamService } from '../services/api'

export default {
  name: 'Teams',
  data() {
    return {
      teams: [],
      loading: true
    }
  },
  async mounted() {
    await this.loadTeams()
  },
  methods: {
    async loadTeams() {
      try {
        const response = await teamService.getAllTeams()
        this.teams = response.data
      } catch (error) {
        console.error('Error loading teams:', error)
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

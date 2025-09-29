<template>
  <div class="container mx-auto px-4 py-8">
    <div v-if="loading" class="text-center">Loading...</div>
    <div v-else-if="error" class="text-red-600 text-center">{{ error }}</div>
    <div v-else-if="athlete">
      <h1 class="text-3xl font-bold mb-6">{{ athlete.firstName }} {{ athlete.lastName }}</h1>
      
      <div class="grid md:grid-cols-2 gap-6 mb-6">
        <div class="bg-white rounded-lg shadow p-6">
          <h2 class="text-xl font-semibold mb-4">Athlete Information</h2>
          <p><strong>Grade:</strong> {{ athlete.grade }}th</p>
          <p><strong>Team:</strong> 
            <router-link v-if="athlete.team" :to="`/teams/${athlete.team.id}`" 
                         class="text-blue-600 hover:underline">
              {{ athlete.team.name }}
            </router-link>
          </p>
          <p v-if="athlete.personalBest"><strong>Personal Best:</strong> {{ athlete.personalBest }}</p>
        </div>
        
        <div class="bg-white rounded-lg shadow p-6">
          <h2 class="text-xl font-semibold mb-4">Season Stats</h2>
          <p><strong>Races:</strong> {{ results.length }}</p>
          <p v-if="bestTime"><strong>Season Best:</strong> {{ bestTime }}</p>
          <p v-if="averageTime"><strong>Average Time:</strong> {{ averageTime }}</p>
        </div>
      </div>

      <div class="bg-white rounded-lg shadow p-6">
        <h2 class="text-xl font-semibold mb-4">Race Results</h2>
        <div v-if="results.length > 0" class="overflow-x-auto">
          <table class="min-w-full table-auto">
            <thead>
              <tr class="bg-gray-50">
                <th class="px-4 py-2 text-left">Date</th>
                <th class="px-4 py-2 text-left">Race</th>
                <th class="px-4 py-2 text-left">Time</th>
                <th class="px-4 py-2 text-left">Place</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="result in results" :key="result.id" class="border-t">
                <td class="px-4 py-2">{{ formatDate(result.race?.date) }}</td>
                <td class="px-4 py-2">{{ result.race?.name }}</td>
                <td class="px-4 py-2">{{ result.time }}</td>
                <td class="px-4 py-2">{{ result.place }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <p v-else class="text-gray-500">No race results found.</p>
      </div>
    </div>
  </div>
</template>

<script>
import { athleteService, resultService } from '../services/api'

export default {
  name: 'AthleteDetail',
  props: ['id'],
  data() {
    return {
      athlete: null,
      results: [],
      loading: true,
      error: null
    }
  },
  computed: {
    bestTime() {
      if (this.results.length === 0) return null
      return Math.min(...this.results.map(r => this.timeToSeconds(r.time)))
    },
    averageTime() {
      if (this.results.length === 0) return null
      const total = this.results.reduce((sum, r) => sum + this.timeToSeconds(r.time), 0)
      return this.secondsToTime(total / this.results.length)
    }
  },
  async mounted() {
    await this.fetchAthlete()
    await this.fetchResults()
  },
  methods: {
    async fetchAthlete() {
      try {
        this.loading = true
        const response = await athleteService.getAthlete(this.id)
        this.athlete = response.data
      } catch (error) {
        this.error = 'Failed to load athlete details'
        console.error('Error fetching athlete:', error)
      } finally {
        this.loading = false
      }
    },
    async fetchResults() {
      try {
        const response = await resultService.getAthleteResults(this.id)
        this.results = response.data
      } catch (error) {
        console.error('Error fetching results:', error)
      }
    },
    formatDate(dateString) {
      if (!dateString) return ''
      return new Date(dateString).toLocaleDateString()
    },
    timeToSeconds(timeString) {
      if (!timeString) return 0
      const parts = timeString.split(':')
      return parseInt(parts[0]) * 60 + parseFloat(parts[1])
    },
    secondsToTime(seconds) {
      const minutes = Math.floor(seconds / 60)
      const secs = (seconds % 60).toFixed(2)
      return `${minutes}:${secs.padStart(5, '0')}`
    }
  }
}
</script>

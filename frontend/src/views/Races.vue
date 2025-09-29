<template>
  <div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-6">Races</h1>

    <div v-if="loading" class="text-center">Loading...</div>
    <div v-else-if="error" class="text-red-600 text-center">{{ error }}</div>
    <div v-else>
      <div class="grid gap-6">
        <div v-for="race in races" :key="race.id" 
             class="bg-white rounded-lg shadow p-6 hover:shadow-lg transition-shadow">
          <div class="flex justify-between items-start mb-4">
            <div>
              <h3 class="text-xl font-semibold text-blue-600">{{ race.name }}</h3>
              <p class="text-gray-600">{{ formatDate(race.date) }}</p>
              <p class="text-gray-600">{{ race.location }}</p>
            </div>
            <span class="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-sm">
              {{ race.division }}
            </span>
          </div>
          
          <div class="grid md:grid-cols-3 gap-4 text-sm">
            <div>
              <strong>Distance:</strong> {{ race.distance || '5K' }}
            </div>
            <div>
              <strong>Weather:</strong> {{ race.weather || 'N/A' }}
            </div>
            <div>
              <strong>Participants:</strong> {{ race.participantCount || 'TBD' }}
            </div>
          </div>

          <div class="mt-4 flex space-x-4">
            <button @click="viewResults(race.id)" 
                    class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
              View Results
            </button>
            <button @click="viewStats(race.id)" 
                    class="bg-gray-600 text-white px-4 py-2 rounded hover:bg-gray-700">
              Statistics
            </button>
          </div>
        </div>
        
        <div v-if="races.length === 0" class="text-center text-gray-500 py-8">
          No races found.
        </div>
      </div>
    </div>

    <!-- Results Modal -->
    <div v-if="showResults" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-lg p-6 max-w-4xl w-full mx-4 max-h-96 overflow-y-auto">
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-xl font-semibold">Race Results</h3>
          <button @click="closeResults" class="text-gray-500 hover:text-gray-700">✕</button>
        </div>
        <div v-if="raceResults.length > 0" class="overflow-x-auto">
          <table class="min-w-full table-auto">
            <thead>
              <tr class="bg-gray-50">
                <th class="px-4 py-2 text-left">Place</th>
                <th class="px-4 py-2 text-left">Athlete</th>
                <th class="px-4 py-2 text-left">Team</th>
                <th class="px-4 py-2 text-left">Time</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="result in raceResults" :key="result.id" class="border-t">
                <td class="px-4 py-2">{{ result.place }}</td>
                <td class="px-4 py-2">
                  <router-link :to="`/athletes/${result.athlete?.id}`" 
                               class="text-blue-600 hover:underline">
                    {{ result.athlete?.firstName }} {{ result.athlete?.lastName }}
                  </router-link>
                </td>
                <td class="px-4 py-2">{{ result.athlete?.team?.name }}</td>
                <td class="px-4 py-2">{{ result.time }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <p v-else class="text-gray-500">No results available for this race.</p>
      </div>
    </div>
  </div>
</template>

<script>
import { raceService, resultService } from '../services/api'

export default {
  name: 'Races',
  data() {
    return {
      races: [],
      raceResults: [],
      showResults: false,
      loading: true,
      error: null
    }
  },
  async mounted() {
    await this.fetchRaces()
  },
  methods: {
    async fetchRaces() {
      try {
        this.loading = true
        const response = await raceService.getAllRaces()
        this.races = response.data
      } catch (error) {
        this.error = 'Failed to load races'
        console.error('Error fetching races:', error)
      } finally {
        this.loading = false
      }
    },
    async viewResults(raceId) {
      try {
        const response = await resultService.getRaceResults(raceId)
        this.raceResults = response.data
        this.showResults = true
      } catch (error) {
        console.error('Error fetching race results:', error)
      }
    },
    viewStats(raceId) {
      // Placeholder for statistics functionality
      alert('Statistics feature coming soon!')
    },
    closeResults() {
      this.showResults = false
      this.raceResults = []
    },
    formatDate(dateString) {
      if (!dateString) return ''
      return new Date(dateString).toLocaleDateString()
    }
  }
}
</script>

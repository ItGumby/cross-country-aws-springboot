<template>
  <div class="space-y-8">
    <div class="text-center">
      <h1 class="text-4xl font-bold text-gray-900 mb-4">
        Cross Country Results
      </h1>
      <p class="text-xl text-gray-600">
        Track team performance, race results, and athlete statistics
      </p>
    </div>

    <!-- Search Section -->
    <div class="max-w-2xl mx-auto">
      <div class="relative">
        <input
          v-model="searchTerm"
          @input="performSearch"
          type="text"
          placeholder="Search for runners or teams..."
          class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
        />
        <div v-if="searchResults.length > 0" class="absolute z-10 w-full bg-white border border-gray-300 rounded-lg mt-1 shadow-lg">
          <div v-for="result in searchResults" :key="result.id" class="p-3 hover:bg-gray-50 cursor-pointer">
            <router-link :to="result.link" class="block">
              <div class="font-medium">{{ result.name }}</div>
              <div class="text-sm text-gray-500">{{ result.type }}</div>
            </router-link>
          </div>
        </div>
      </div>
    </div>

    <!-- Quick Navigation -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
      <router-link to="/teams" class="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
        <h3 class="text-xl font-semibold text-gray-900 mb-2">Teams</h3>
        <p class="text-gray-600">Browse team rosters and performance</p>
      </router-link>
      
      <router-link to="/athletes" class="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
        <h3 class="text-xl font-semibold text-gray-900 mb-2">Athletes</h3>
        <p class="text-gray-600">View individual runner profiles</p>
      </router-link>
      
      <router-link to="/races" class="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
        <h3 class="text-xl font-semibold text-gray-900 mb-2">Races</h3>
        <p class="text-gray-600">Explore race results and statistics</p>
      </router-link>
    </div>

    <!-- Recent Races -->
    <div class="bg-white rounded-lg shadow-md p-6">
      <h2 class="text-2xl font-bold text-gray-900 mb-4">Recent Races</h2>
      <div v-if="loading" class="text-center py-4">Loading...</div>
      <div v-else-if="recentRaces.length === 0" class="text-center py-4 text-gray-500">
        No recent races found
      </div>
      <div v-else class="space-y-4">
        <div v-for="race in recentRaces" :key="race.id" class="border-l-4 border-blue-500 pl-4">
          <h3 class="font-semibold">{{ race.meetName }}</h3>
          <p class="text-sm text-gray-600">{{ formatDate(race.date) }} • {{ race.location }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { teamService, athleteService, raceService } from '../services/api'

export default {
  name: 'Home',
  data() {
    return {
      searchTerm: '',
      searchResults: [],
      recentRaces: [],
      loading: true
    }
  },
  async mounted() {
    await this.loadRecentRaces()
  },
  methods: {
    async loadRecentRaces() {
      try {
        const response = await raceService.getRecentRaces()
        this.recentRaces = response.data.slice(0, 5)
      } catch (error) {
        console.error('Error loading recent races:', error)
      } finally {
        this.loading = false
      }
    },
    async performSearch() {
      if (this.searchTerm.length < 2) {
        this.searchResults = []
        return
      }
      
      try {
        const [teamResults, athleteResults] = await Promise.all([
          teamService.searchTeams(this.searchTerm),
          athleteService.searchAthletes(this.searchTerm)
        ])
        
        this.searchResults = [
          ...teamResults.data.map(team => ({
            id: `team-${team.id}`,
            name: team.schoolName,
            type: 'Team',
            link: `/teams/${team.id}`
          })),
          ...athleteResults.data.map(athlete => ({
            id: `athlete-${athlete.id}`,
            name: athlete.name,
            type: 'Athlete',
            link: `/athletes/${athlete.id}`
          }))
        ].slice(0, 10)
      } catch (error) {
        console.error('Search error:', error)
      }
    },
    formatDate(dateString) {
      return new Date(dateString).toLocaleDateString()
    }
  }
}
</script>

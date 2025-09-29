<template>
  <div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-6">Athletes</h1>
    
    <div class="mb-6">
      <input v-model="searchQuery" @input="searchAthletes" 
             placeholder="Search athletes..." 
             class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>

    <div v-if="loading" class="text-center">Loading...</div>
    <div v-else-if="error" class="text-red-600 text-center">{{ error }}</div>
    <div v-else class="grid gap-4">
      <div v-for="athlete in athletes" :key="athlete.id" 
           class="bg-white rounded-lg shadow p-6 hover:shadow-lg transition-shadow">
        <router-link :to="`/athletes/${athlete.id}`" class="block">
          <h3 class="text-xl font-semibold text-blue-600 hover:underline">
            {{ athlete.firstName }} {{ athlete.lastName }}
          </h3>
          <p class="text-gray-600">{{ athlete.grade }}th Grade</p>
          <p class="text-gray-600">{{ athlete.team?.name }}</p>
        </router-link>
      </div>
      <div v-if="athletes.length === 0" class="text-center text-gray-500 py-8">
        No athletes found.
      </div>
    </div>
  </div>
</template>

<script>
import { athleteService } from '../services/api'

export default {
  name: 'Athletes',
  data() {
    return {
      athletes: [],
      searchQuery: '',
      loading: true,
      error: null
    }
  },
  async mounted() {
    await this.fetchAthletes()
  },
  methods: {
    async fetchAthletes() {
      try {
        this.loading = true
        const response = await athleteService.getAllAthletes()
        this.athletes = response.data
      } catch (error) {
        this.error = 'Failed to load athletes'
        console.error('Error fetching athletes:', error)
      } finally {
        this.loading = false
      }
    },
    async searchAthletes() {
      if (this.searchQuery.trim()) {
        try {
          const response = await athleteService.searchAthletes(this.searchQuery)
          this.athletes = response.data
        } catch (error) {
          console.error('Error searching athletes:', error)
        }
      } else {
        await this.fetchAthletes()
      }
    }
  }
}
</script>

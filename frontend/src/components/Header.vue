<template>
  <header class="bg-blue-600 text-white shadow-lg">
    <nav class="container mx-auto px-4 py-4">
      <div class="flex justify-between items-center">
        <router-link to="/" class="text-2xl font-bold">
          Cross Country Results
        </router-link>
        <div class="space-x-4">
          <router-link to="/" class="hover:text-blue-200">Home</router-link>
          <router-link to="/teams" class="hover:text-blue-200">Teams</router-link>
          <router-link to="/athletes" class="hover:text-blue-200">Athletes</router-link>
          <router-link to="/races" class="hover:text-blue-200">Races</router-link>
        </div>
      </div>
    </nav>
  </header>
</template>

<script>
export default {
  name: 'Header'
}
</script>

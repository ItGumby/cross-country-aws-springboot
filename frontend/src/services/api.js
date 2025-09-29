import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

export const teamService = {
  getAllTeams: () => api.get('/teams'),
  getTeam: (id) => api.get(`/teams/${id}`),
  searchTeams: (name) => api.get(`/teams/search?name=${name}`),
  createTeam: (team) => api.post('/teams', team),
  updateTeam: (id, team) => api.put(`/teams/${id}`, team),
  deleteTeam: (id) => api.delete(`/teams/${id}`)
}

export const athleteService = {
  getAllAthletes: () => api.get('/athletes'),
  getAthlete: (id) => api.get(`/athletes/${id}`),
  searchAthletes: (name) => api.get(`/athletes/search?name=${name}`)
}

export const raceService = {
  getAllRaces: () => api.get('/races'),
  getRace: (id) => api.get(`/races/${id}`),
  getRecentRaces: () => api.get('/races/recent')
}

export const resultService = {
  getAthleteResults: (athleteId) => api.get(`/results/athlete/${athleteId}`),
  getTeamResults: (teamId) => api.get(`/results/team/${teamId}`),
  getRaceResults: (raceId) => api.get(`/results/race/${raceId}`)
}

export default api

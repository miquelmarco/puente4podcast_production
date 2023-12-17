setTimeout(() => {
    let { createApp } = Vue;
    createApp({
        data() {
            return {
                current: [],
                episodes: [],
                filteredEp: [],
                seasons: [],
                checked: [],
                searchInput: '',
                isLoading: false
            }
        },
        created() {
            this.getCurrent()
            this.getEpisodes()
        },
        methods: {
            logOut() {
                axios.post("/api/logout")
                    .then(res => {
                        if (res.status == 200) {
                            Swal.fire({
                                position: 'center',
                                // icon: 'success',
                                title: 'Bye bye!',
                                showConfirmButton: false,
                                timer: 1500
                            })
                            setTimeout(() => {
                                window.location.href = "/web/index.html";
                            }, 1800)
                        }
                        console.log(res)
                    }).catch(err => {
                        Swal.fire({
                            position: 'center',
                            title: 'Cant log out, try again!',
                            showConfirmButton: false,
                            timer: 1500
                        })
                    })
            },
            getCurrent() {
                axios.get(`/api/getCurrent`)
                    .then(res => {
                        this.current = res.data
                    }).catch(err => console.log(err))
            },
            getEpisodes() {
                axios.get(`/api/episodes`)
                    .then(res => {
                        this.episodes = res.data
                        this.seasons = Array.from(new Set(this.episodes.map(episode => episode.seasonNumber)))
                    }).catch(err => console.log(err))
            },
            addEpFav(id) {
                if (this.current.length != 0) {
                    axios.post(`/api/favorite/addEpFav`, `id=${id}`)
                        .then(res => {
                            this.backMsg = res.data
                            Swal.fire({
                                position: 'center',
                                // icon: 'success',
                                title: `${this.backMsg}`,
                                showConfirmButton: false,
                                timer: 700
                            })
                        }).catch(err => {
                            this.backMsg = err.response.data
                            Swal.fire({
                                position: 'center',
                                // icon: 'error',
                                title: `${this.backMsg}`,
                                showConfirmButton: false,
                                timer: 1000
                            })
                        })
                } else {
                    Swal.fire({
                        position: 'center',
                        // icon: 'error',
                        title: `Debes estar logueado`,
                        showConfirmButton: false,
                        timer: 1500
                    })
                }
            }
        },
        computed: {
            filtroEpCheckSearch() {
                this.filteredEp = this.episodes.filter(episode => episode.name.toLowerCase().includes(this.searchInput.toLowerCase())
                    && (this.checked.includes(episode.seasonNumber) || this.checked.length == 0)).sort((a, b) => b.id - a.id)
            }
        }
    }).mount("#app")
}, 1000)
const state = {
  textInfo: null
}

const mutations = {
  updateTextInfo (state, textInfo) {
    state.textInfo = textInfo
  }
}

const actions = {
  updateTextInfo ({ commit }, textInfo) {
    // do something async
    commit('updateTextInfo', textInfo)
  }
}

export default {
  state,
  mutations,
  actions
}

import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import common from './modules/common'

Vue.use(Vuex)

export default new Vuex.Store({
  getters,
  modules: {
    common
  }
})
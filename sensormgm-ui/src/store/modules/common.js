import * as types from '../mutation-types'

const state = {
  count: 1,
  currentTab: {},
  menuTabs: [],
  leftBarMenus: [],
  userInfo: {},
  menu: []
}

const actions = {
  setMenu ({ // 菜单
    commit
  }, data) {
    commit(types.SET_MENU, data)
  },
  setUserInfo ({ // 当前用户
    commit
  }, data) {
    commit(types.SET_USER_INFO, data)
  },
  setCount ({ // 数字递增
    commit
  }) {
    commit(types.SET_COUNT)
  },
  setCurrentTab ({ // 当前tab实例
    commit
  }, tab) {
    commit(types.SET_CURRENT_TAB, tab)
  },
  addMenuTabs ({ // 当前已选tabs列表
    commit
  }, tab) {
    commit(types.ADD_MENU_TABS, tab)
  },
  removeMenuTabs ({ // 去除选择的tab
    commit
  }, tab) {
    commit(types.REMOVE_MENU_TABS, tab)
  },
  clearMenuTabs ({ // 清除当前已选tabs列表
    commit
  }) {
    commit(types.CLEAR_MENU_TABS)
  }
}

const mutations = {
  [types.SET_COUNT] (state) {
    state.count++
  },
  [types.SET_CURRENT_TAB] (state, tab) {
    state.currentTab = tab
  },
  [types.ADD_MENU_TABS] (state, tab) {
    if (tab.name === 'dashboard' || tab.name === 'page404') {
      return false
    }
    let t = state.menuTabs.find((tempTab) => {
      return tempTab.name === tab.name
    })
    if (!t) {
      state.menuTabs.push(tab)
    }
  },
  [types.REMOVE_MENU_TABS] (state, tab) {
    let index = state.menuTabs.findIndex(tempTab => {
      return tempTab.name === tab.name
    })
    if (index) {
      state.menuTabs.splice(index, 1)
    }
  },
  [types.CLEAR_MENU_TABS] (state) {
    state.menuTabs = []
  },
  [types.SET_USER_INFO] (state, data) {
    state.userInfo = data
  },
  [types.SET_MENU] (state, data) {
    state.menu = data
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations
}

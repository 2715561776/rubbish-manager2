import * as actions from './actions'
import * as getters from './getters'

// initial state
const initialState = {
  objs: [],
  current: {},
  total:0
}

// mutations
const mutations = {
    cleanCurrentPlaceInfoByState (state, body) {
        state.current = {};
    },
    queryPlaceInfoSuccess (state, body) {
        const code = body.code;
        if(code == "200"){
            state.objs = body.data.records
            state.total = body.data.total;
        }else if(code == "500"){
            console.log("获取失败:"+body.msg);
        }
    },
    findPlaceInfoByState (state, data) {
        const record = state.objs.find(p => p.id === data.id);
        if (!record) {
            return null;
        } else {
            state.current = record;
            return record;
        }
    },
    findByPlaceInfoIdSuccess (state, body) {
        const code = body.code;
        if(code == "200"){
            const data = body.data;
            state.current = data;
            state.objs.push(data);
            console.log("获取成功！");
        }else if(code == "500"){
            console.log("获取失败:"+body.msg);
        }
    },
    savePlaceInfoSuccess (state, body) {
        const code = body.code;
        if(code == "200"){
            const data = body.data;
            state.objs.push(data);
            state.current = {};
        }else if(code == "500"){
            console.log("新增失败:"+body.msg);
        }
    },
    updatePlaceInfoSuccess (state, body) {
        const code = body.code;
        if(code == "200"){
            const data = state.current;
            const index = state.objs.findIndex((p) => p.id === data.id);
            if (index !== -1) {
                state.objs.splice(index, 1, data)
            }
        }else if(code == "500"){
            console.log("更新失败:"+body.msg);
        }
    },
    deletePlaceInfoSuccess(state, body) {
        const code = body.code;
        if(code == "200"){
            // const data = body.data;
            const data = state.current;
            state.objs = state.objs.filter(p => p.id !== data.id)
            state.total--;
        }else if(code == "500"){
            console.log("删除失败:"+body.msg);
        }
    },
    batchDeletePlaceInfoSuccess(state, body) {
        const code = body.code;
        if(code == "200"){
            const data = body.data;
            for(var i = 0;i < data.length; i++) {
                state.objs = state.objs.filter(p => p.id !== data[i].id)
                state.total--;
            }
        }else if(code == "500"){
            console.log("删除失败:"+body.msg);
        }
    }
}

export default {
  state: { ...initialState },
  actions,
  getters,
  mutations
}

import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAdjustShoping, defaultValue } from 'app/shared/model/adjust-shoping.model';

export const ACTION_TYPES = {
  FETCH_ADJUSTSHOPING_LIST: 'adjustShoping/FETCH_ADJUSTSHOPING_LIST',
  FETCH_ADJUSTSHOPING: 'adjustShoping/FETCH_ADJUSTSHOPING',
  CREATE_ADJUSTSHOPING: 'adjustShoping/CREATE_ADJUSTSHOPING',
  UPDATE_ADJUSTSHOPING: 'adjustShoping/UPDATE_ADJUSTSHOPING',
  DELETE_ADJUSTSHOPING: 'adjustShoping/DELETE_ADJUSTSHOPING',
  SET_BLOB: 'adjustShoping/SET_BLOB',
  RESET: 'adjustShoping/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAdjustShoping>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AdjustShopingState = Readonly<typeof initialState>;

// Reducer

export default (state: AdjustShopingState = initialState, action): AdjustShopingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ADJUSTSHOPING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADJUSTSHOPING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ADJUSTSHOPING):
    case REQUEST(ACTION_TYPES.UPDATE_ADJUSTSHOPING):
    case REQUEST(ACTION_TYPES.DELETE_ADJUSTSHOPING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ADJUSTSHOPING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADJUSTSHOPING):
    case FAILURE(ACTION_TYPES.CREATE_ADJUSTSHOPING):
    case FAILURE(ACTION_TYPES.UPDATE_ADJUSTSHOPING):
    case FAILURE(ACTION_TYPES.DELETE_ADJUSTSHOPING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUSTSHOPING_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUSTSHOPING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADJUSTSHOPING):
    case SUCCESS(ACTION_TYPES.UPDATE_ADJUSTSHOPING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADJUSTSHOPING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/adjust-shopings';

// Actions

export const getEntities: ICrudGetAllAction<IAdjustShoping> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ADJUSTSHOPING_LIST,
  payload: axios.get<IAdjustShoping>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAdjustShoping> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADJUSTSHOPING,
    payload: axios.get<IAdjustShoping>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAdjustShoping> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADJUSTSHOPING,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdjustShoping> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADJUSTSHOPING,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdjustShoping> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADJUSTSHOPING,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAdjustMeal, defaultValue } from 'app/shared/model/adjust-meal.model';

export const ACTION_TYPES = {
  FETCH_ADJUSTMEAL_LIST: 'adjustMeal/FETCH_ADJUSTMEAL_LIST',
  FETCH_ADJUSTMEAL: 'adjustMeal/FETCH_ADJUSTMEAL',
  CREATE_ADJUSTMEAL: 'adjustMeal/CREATE_ADJUSTMEAL',
  UPDATE_ADJUSTMEAL: 'adjustMeal/UPDATE_ADJUSTMEAL',
  DELETE_ADJUSTMEAL: 'adjustMeal/DELETE_ADJUSTMEAL',
  RESET: 'adjustMeal/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAdjustMeal>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AdjustMealState = Readonly<typeof initialState>;

// Reducer

export default (state: AdjustMealState = initialState, action): AdjustMealState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ADJUSTMEAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADJUSTMEAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ADJUSTMEAL):
    case REQUEST(ACTION_TYPES.UPDATE_ADJUSTMEAL):
    case REQUEST(ACTION_TYPES.DELETE_ADJUSTMEAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ADJUSTMEAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADJUSTMEAL):
    case FAILURE(ACTION_TYPES.CREATE_ADJUSTMEAL):
    case FAILURE(ACTION_TYPES.UPDATE_ADJUSTMEAL):
    case FAILURE(ACTION_TYPES.DELETE_ADJUSTMEAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUSTMEAL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUSTMEAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADJUSTMEAL):
    case SUCCESS(ACTION_TYPES.UPDATE_ADJUSTMEAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADJUSTMEAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/adjust-meals';

// Actions

export const getEntities: ICrudGetAllAction<IAdjustMeal> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ADJUSTMEAL_LIST,
  payload: axios.get<IAdjustMeal>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAdjustMeal> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADJUSTMEAL,
    payload: axios.get<IAdjustMeal>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAdjustMeal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADJUSTMEAL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdjustMeal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADJUSTMEAL,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdjustMeal> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADJUSTMEAL,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

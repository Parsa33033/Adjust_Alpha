import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AdjustMeal from './adjust-meal';
import AdjustMealDetail from './adjust-meal-detail';
import AdjustMealUpdate from './adjust-meal-update';
import AdjustMealDeleteDialog from './adjust-meal-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdjustMealUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdjustMealUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdjustMealDetail} />
      <ErrorBoundaryRoute path={match.url} component={AdjustMeal} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AdjustMealDeleteDialog} />
  </>
);

export default Routes;

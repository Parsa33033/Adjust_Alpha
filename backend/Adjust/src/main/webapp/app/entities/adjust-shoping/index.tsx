import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AdjustShoping from './adjust-shoping';
import AdjustShopingDetail from './adjust-shoping-detail';
import AdjustShopingUpdate from './adjust-shoping-update';
import AdjustShopingDeleteDialog from './adjust-shoping-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdjustShopingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdjustShopingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdjustShopingDetail} />
      <ErrorBoundaryRoute path={match.url} component={AdjustShoping} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AdjustShopingDeleteDialog} />
  </>
);

export default Routes;

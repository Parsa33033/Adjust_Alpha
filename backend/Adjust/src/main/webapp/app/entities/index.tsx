import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AdjustClient from './adjust-client';
import Specialist from './specialist';
import AdjustProgram from './adjust-program';
import BodyComposition from './body-composition';
import FitnessProgram from './fitness-program';
import Workout from './workout';
import Exercise from './exercise';
import Move from './move';
import NutritionProgram from './nutrition-program';
import Meal from './meal';
import Conversation from './conversation';
import ChatMessage from './chat-message';
import AdjustTutorial from './adjust-tutorial';
import AdjustTokens from './adjust-tokens';
import AdjustPrice from './adjust-price';
import AdjustShoping from './adjust-shoping';
import AdjustShopingItem from './adjust-shoping-item';
import Cart from './cart';
import Order from './order';
import ShopingItem from './shoping-item';
import AdjustMove from './adjust-move';
import AdjustMeal from './adjust-meal';
import AdjustTutorialVideo from './adjust-tutorial-video';
import Tutorial from './tutorial';
import TutorialVideo from './tutorial-video';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}adjust-client`} component={AdjustClient} />
      <ErrorBoundaryRoute path={`${match.url}specialist`} component={Specialist} />
      <ErrorBoundaryRoute path={`${match.url}adjust-program`} component={AdjustProgram} />
      <ErrorBoundaryRoute path={`${match.url}body-composition`} component={BodyComposition} />
      <ErrorBoundaryRoute path={`${match.url}fitness-program`} component={FitnessProgram} />
      <ErrorBoundaryRoute path={`${match.url}workout`} component={Workout} />
      <ErrorBoundaryRoute path={`${match.url}exercise`} component={Exercise} />
      <ErrorBoundaryRoute path={`${match.url}move`} component={Move} />
      <ErrorBoundaryRoute path={`${match.url}nutrition-program`} component={NutritionProgram} />
      <ErrorBoundaryRoute path={`${match.url}meal`} component={Meal} />
      <ErrorBoundaryRoute path={`${match.url}conversation`} component={Conversation} />
      <ErrorBoundaryRoute path={`${match.url}chat-message`} component={ChatMessage} />
      <ErrorBoundaryRoute path={`${match.url}adjust-tutorial`} component={AdjustTutorial} />
      <ErrorBoundaryRoute path={`${match.url}adjust-tokens`} component={AdjustTokens} />
      <ErrorBoundaryRoute path={`${match.url}adjust-price`} component={AdjustPrice} />
      <ErrorBoundaryRoute path={`${match.url}adjust-shoping`} component={AdjustShoping} />
      <ErrorBoundaryRoute path={`${match.url}adjust-shoping-item`} component={AdjustShopingItem} />
      <ErrorBoundaryRoute path={`${match.url}cart`} component={Cart} />
      <ErrorBoundaryRoute path={`${match.url}order`} component={Order} />
      <ErrorBoundaryRoute path={`${match.url}shoping-item`} component={ShopingItem} />
      <ErrorBoundaryRoute path={`${match.url}adjust-move`} component={AdjustMove} />
      <ErrorBoundaryRoute path={`${match.url}adjust-meal`} component={AdjustMeal} />
      <ErrorBoundaryRoute path={`${match.url}adjust-tutorial-video`} component={AdjustTutorialVideo} />
      <ErrorBoundaryRoute path={`${match.url}tutorial`} component={Tutorial} />
      <ErrorBoundaryRoute path={`${match.url}tutorial-video`} component={TutorialVideo} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;

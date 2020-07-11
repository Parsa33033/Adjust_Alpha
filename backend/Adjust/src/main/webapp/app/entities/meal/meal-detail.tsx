import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './meal.reducer';
import { IMeal } from 'app/shared/model/meal.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMealDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MealDetail = (props: IMealDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { mealEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="adjustApp.meal.detail.title">Meal</Translate> [<b>{mealEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="adjustApp.meal.name">Name</Translate>
            </span>
          </dt>
          <dd>{mealEntity.name}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="adjustApp.meal.number">Number</Translate>
            </span>
          </dt>
          <dd>{mealEntity.number}</dd>
          <dt>
            <span id="lowFatDairyUnit">
              <Translate contentKey="adjustApp.meal.lowFatDairyUnit">Low Fat Dairy Unit</Translate>
            </span>
          </dt>
          <dd>{mealEntity.lowFatDairyUnit}</dd>
          <dt>
            <span id="mediumFatDairyUnit">
              <Translate contentKey="adjustApp.meal.mediumFatDairyUnit">Medium Fat Dairy Unit</Translate>
            </span>
          </dt>
          <dd>{mealEntity.mediumFatDairyUnit}</dd>
          <dt>
            <span id="highFatDairyUnit">
              <Translate contentKey="adjustApp.meal.highFatDairyUnit">High Fat Dairy Unit</Translate>
            </span>
          </dt>
          <dd>{mealEntity.highFatDairyUnit}</dd>
          <dt>
            <span id="lowFatMeatUnit">
              <Translate contentKey="adjustApp.meal.lowFatMeatUnit">Low Fat Meat Unit</Translate>
            </span>
          </dt>
          <dd>{mealEntity.lowFatMeatUnit}</dd>
          <dt>
            <span id="mediumFatMeatUnit">
              <Translate contentKey="adjustApp.meal.mediumFatMeatUnit">Medium Fat Meat Unit</Translate>
            </span>
          </dt>
          <dd>{mealEntity.mediumFatMeatUnit}</dd>
          <dt>
            <span id="highFatMeatUnti">
              <Translate contentKey="adjustApp.meal.highFatMeatUnti">High Fat Meat Unti</Translate>
            </span>
          </dt>
          <dd>{mealEntity.highFatMeatUnti}</dd>
          <dt>
            <span id="breadUnit">
              <Translate contentKey="adjustApp.meal.breadUnit">Bread Unit</Translate>
            </span>
          </dt>
          <dd>{mealEntity.breadUnit}</dd>
          <dt>
            <span id="cerealUnit">
              <Translate contentKey="adjustApp.meal.cerealUnit">Cereal Unit</Translate>
            </span>
          </dt>
          <dd>{mealEntity.cerealUnit}</dd>
          <dt>
            <span id="riceUnit">
              <Translate contentKey="adjustApp.meal.riceUnit">Rice Unit</Translate>
            </span>
          </dt>
          <dd>{mealEntity.riceUnit}</dd>
          <dt>
            <span id="pastaUnit">
              <Translate contentKey="adjustApp.meal.pastaUnit">Pasta Unit</Translate>
            </span>
          </dt>
          <dd>{mealEntity.pastaUnit}</dd>
          <dt>
            <span id="fruitUnit">
              <Translate contentKey="adjustApp.meal.fruitUnit">Fruit Unit</Translate>
            </span>
          </dt>
          <dd>{mealEntity.fruitUnit}</dd>
          <dt>
            <span id="vegetableUnit">
              <Translate contentKey="adjustApp.meal.vegetableUnit">Vegetable Unit</Translate>
            </span>
          </dt>
          <dd>{mealEntity.vegetableUnit}</dd>
          <dt>
            <span id="fatUnit">
              <Translate contentKey="adjustApp.meal.fatUnit">Fat Unit</Translate>
            </span>
          </dt>
          <dd>{mealEntity.fatUnit}</dd>
          <dt>
            <Translate contentKey="adjustApp.meal.nutritionProgram">Nutrition Program</Translate>
          </dt>
          <dd>{mealEntity.nutritionProgramId ? mealEntity.nutritionProgramId : ''}</dd>
        </dl>
        <Button tag={Link} to="/meal" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/meal/${mealEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ meal }: IRootState) => ({
  mealEntity: meal.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MealDetail);

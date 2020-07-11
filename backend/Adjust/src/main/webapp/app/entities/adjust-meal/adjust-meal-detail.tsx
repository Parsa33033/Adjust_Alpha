import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './adjust-meal.reducer';
import { IAdjustMeal } from 'app/shared/model/adjust-meal.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdjustMealDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdjustMealDetail = (props: IAdjustMealDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { adjustMealEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="adjustApp.adjustMeal.detail.title">AdjustMeal</Translate> [<b>{adjustMealEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="adjustApp.adjustMeal.name">Name</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.name}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="adjustApp.adjustMeal.number">Number</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.number}</dd>
          <dt>
            <span id="lowFatDairyUnit">
              <Translate contentKey="adjustApp.adjustMeal.lowFatDairyUnit">Low Fat Dairy Unit</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.lowFatDairyUnit}</dd>
          <dt>
            <span id="mediumFatDairyUnit">
              <Translate contentKey="adjustApp.adjustMeal.mediumFatDairyUnit">Medium Fat Dairy Unit</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.mediumFatDairyUnit}</dd>
          <dt>
            <span id="highFatDairyUnit">
              <Translate contentKey="adjustApp.adjustMeal.highFatDairyUnit">High Fat Dairy Unit</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.highFatDairyUnit}</dd>
          <dt>
            <span id="lowFatMeatUnit">
              <Translate contentKey="adjustApp.adjustMeal.lowFatMeatUnit">Low Fat Meat Unit</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.lowFatMeatUnit}</dd>
          <dt>
            <span id="mediumFatMeatUnit">
              <Translate contentKey="adjustApp.adjustMeal.mediumFatMeatUnit">Medium Fat Meat Unit</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.mediumFatMeatUnit}</dd>
          <dt>
            <span id="highFatMeatUnti">
              <Translate contentKey="adjustApp.adjustMeal.highFatMeatUnti">High Fat Meat Unti</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.highFatMeatUnti}</dd>
          <dt>
            <span id="breadUnit">
              <Translate contentKey="adjustApp.adjustMeal.breadUnit">Bread Unit</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.breadUnit}</dd>
          <dt>
            <span id="cerealUnit">
              <Translate contentKey="adjustApp.adjustMeal.cerealUnit">Cereal Unit</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.cerealUnit}</dd>
          <dt>
            <span id="riceUnit">
              <Translate contentKey="adjustApp.adjustMeal.riceUnit">Rice Unit</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.riceUnit}</dd>
          <dt>
            <span id="pastaUnit">
              <Translate contentKey="adjustApp.adjustMeal.pastaUnit">Pasta Unit</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.pastaUnit}</dd>
          <dt>
            <span id="fruitUnit">
              <Translate contentKey="adjustApp.adjustMeal.fruitUnit">Fruit Unit</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.fruitUnit}</dd>
          <dt>
            <span id="vegetableUnit">
              <Translate contentKey="adjustApp.adjustMeal.vegetableUnit">Vegetable Unit</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.vegetableUnit}</dd>
          <dt>
            <span id="fatUnit">
              <Translate contentKey="adjustApp.adjustMeal.fatUnit">Fat Unit</Translate>
            </span>
          </dt>
          <dd>{adjustMealEntity.fatUnit}</dd>
        </dl>
        <Button tag={Link} to="/adjust-meal" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adjust-meal/${adjustMealEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ adjustMeal }: IRootState) => ({
  adjustMealEntity: adjustMeal.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdjustMealDetail);

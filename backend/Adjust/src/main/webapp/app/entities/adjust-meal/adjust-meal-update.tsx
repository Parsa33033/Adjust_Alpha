import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './adjust-meal.reducer';
import { IAdjustMeal } from 'app/shared/model/adjust-meal.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAdjustMealUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdjustMealUpdate = (props: IAdjustMealUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { adjustMealEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/adjust-meal');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...adjustMealEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="adjustApp.adjustMeal.home.createOrEditLabel">
            <Translate contentKey="adjustApp.adjustMeal.home.createOrEditLabel">Create or edit a AdjustMeal</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : adjustMealEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="adjust-meal-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="adjust-meal-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="adjust-meal-name">
                  <Translate contentKey="adjustApp.adjustMeal.name">Name</Translate>
                </Label>
                <AvField id="adjust-meal-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="numberLabel" for="adjust-meal-number">
                  <Translate contentKey="adjustApp.adjustMeal.number">Number</Translate>
                </Label>
                <AvField id="adjust-meal-number" type="string" className="form-control" name="number" />
              </AvGroup>
              <AvGroup>
                <Label id="lowFatDairyUnitLabel" for="adjust-meal-lowFatDairyUnit">
                  <Translate contentKey="adjustApp.adjustMeal.lowFatDairyUnit">Low Fat Dairy Unit</Translate>
                </Label>
                <AvField id="adjust-meal-lowFatDairyUnit" type="string" className="form-control" name="lowFatDairyUnit" />
              </AvGroup>
              <AvGroup>
                <Label id="mediumFatDairyUnitLabel" for="adjust-meal-mediumFatDairyUnit">
                  <Translate contentKey="adjustApp.adjustMeal.mediumFatDairyUnit">Medium Fat Dairy Unit</Translate>
                </Label>
                <AvField id="adjust-meal-mediumFatDairyUnit" type="string" className="form-control" name="mediumFatDairyUnit" />
              </AvGroup>
              <AvGroup>
                <Label id="highFatDairyUnitLabel" for="adjust-meal-highFatDairyUnit">
                  <Translate contentKey="adjustApp.adjustMeal.highFatDairyUnit">High Fat Dairy Unit</Translate>
                </Label>
                <AvField id="adjust-meal-highFatDairyUnit" type="string" className="form-control" name="highFatDairyUnit" />
              </AvGroup>
              <AvGroup>
                <Label id="lowFatMeatUnitLabel" for="adjust-meal-lowFatMeatUnit">
                  <Translate contentKey="adjustApp.adjustMeal.lowFatMeatUnit">Low Fat Meat Unit</Translate>
                </Label>
                <AvField id="adjust-meal-lowFatMeatUnit" type="string" className="form-control" name="lowFatMeatUnit" />
              </AvGroup>
              <AvGroup>
                <Label id="mediumFatMeatUnitLabel" for="adjust-meal-mediumFatMeatUnit">
                  <Translate contentKey="adjustApp.adjustMeal.mediumFatMeatUnit">Medium Fat Meat Unit</Translate>
                </Label>
                <AvField id="adjust-meal-mediumFatMeatUnit" type="string" className="form-control" name="mediumFatMeatUnit" />
              </AvGroup>
              <AvGroup>
                <Label id="highFatMeatUntiLabel" for="adjust-meal-highFatMeatUnti">
                  <Translate contentKey="adjustApp.adjustMeal.highFatMeatUnti">High Fat Meat Unti</Translate>
                </Label>
                <AvField id="adjust-meal-highFatMeatUnti" type="string" className="form-control" name="highFatMeatUnti" />
              </AvGroup>
              <AvGroup>
                <Label id="breadUnitLabel" for="adjust-meal-breadUnit">
                  <Translate contentKey="adjustApp.adjustMeal.breadUnit">Bread Unit</Translate>
                </Label>
                <AvField id="adjust-meal-breadUnit" type="string" className="form-control" name="breadUnit" />
              </AvGroup>
              <AvGroup>
                <Label id="cerealUnitLabel" for="adjust-meal-cerealUnit">
                  <Translate contentKey="adjustApp.adjustMeal.cerealUnit">Cereal Unit</Translate>
                </Label>
                <AvField id="adjust-meal-cerealUnit" type="string" className="form-control" name="cerealUnit" />
              </AvGroup>
              <AvGroup>
                <Label id="riceUnitLabel" for="adjust-meal-riceUnit">
                  <Translate contentKey="adjustApp.adjustMeal.riceUnit">Rice Unit</Translate>
                </Label>
                <AvField id="adjust-meal-riceUnit" type="string" className="form-control" name="riceUnit" />
              </AvGroup>
              <AvGroup>
                <Label id="pastaUnitLabel" for="adjust-meal-pastaUnit">
                  <Translate contentKey="adjustApp.adjustMeal.pastaUnit">Pasta Unit</Translate>
                </Label>
                <AvField id="adjust-meal-pastaUnit" type="string" className="form-control" name="pastaUnit" />
              </AvGroup>
              <AvGroup>
                <Label id="fruitUnitLabel" for="adjust-meal-fruitUnit">
                  <Translate contentKey="adjustApp.adjustMeal.fruitUnit">Fruit Unit</Translate>
                </Label>
                <AvField id="adjust-meal-fruitUnit" type="string" className="form-control" name="fruitUnit" />
              </AvGroup>
              <AvGroup>
                <Label id="vegetableUnitLabel" for="adjust-meal-vegetableUnit">
                  <Translate contentKey="adjustApp.adjustMeal.vegetableUnit">Vegetable Unit</Translate>
                </Label>
                <AvField id="adjust-meal-vegetableUnit" type="string" className="form-control" name="vegetableUnit" />
              </AvGroup>
              <AvGroup>
                <Label id="fatUnitLabel" for="adjust-meal-fatUnit">
                  <Translate contentKey="adjustApp.adjustMeal.fatUnit">Fat Unit</Translate>
                </Label>
                <AvField id="adjust-meal-fatUnit" type="string" className="form-control" name="fatUnit" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/adjust-meal" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  adjustMealEntity: storeState.adjustMeal.entity,
  loading: storeState.adjustMeal.loading,
  updating: storeState.adjustMeal.updating,
  updateSuccess: storeState.adjustMeal.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdjustMealUpdate);

import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './adjust-meal.reducer';
import { IAdjustMeal } from 'app/shared/model/adjust-meal.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdjustMealProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const AdjustMeal = (props: IAdjustMealProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { adjustMealList, match, loading } = props;
  return (
    <div>
      <h2 id="adjust-meal-heading">
        <Translate contentKey="adjustApp.adjustMeal.home.title">Adjust Meals</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="adjustApp.adjustMeal.home.createLabel">Create new Adjust Meal</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {adjustMealList && adjustMealList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.number">Number</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.lowFatDairyUnit">Low Fat Dairy Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.mediumFatDairyUnit">Medium Fat Dairy Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.highFatDairyUnit">High Fat Dairy Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.lowFatMeatUnit">Low Fat Meat Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.mediumFatMeatUnit">Medium Fat Meat Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.highFatMeatUnti">High Fat Meat Unti</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.breadUnit">Bread Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.cerealUnit">Cereal Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.riceUnit">Rice Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.pastaUnit">Pasta Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.fruitUnit">Fruit Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.vegetableUnit">Vegetable Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustMeal.fatUnit">Fat Unit</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {adjustMealList.map((adjustMeal, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${adjustMeal.id}`} color="link" size="sm">
                      {adjustMeal.id}
                    </Button>
                  </td>
                  <td>{adjustMeal.name}</td>
                  <td>{adjustMeal.number}</td>
                  <td>{adjustMeal.lowFatDairyUnit}</td>
                  <td>{adjustMeal.mediumFatDairyUnit}</td>
                  <td>{adjustMeal.highFatDairyUnit}</td>
                  <td>{adjustMeal.lowFatMeatUnit}</td>
                  <td>{adjustMeal.mediumFatMeatUnit}</td>
                  <td>{adjustMeal.highFatMeatUnti}</td>
                  <td>{adjustMeal.breadUnit}</td>
                  <td>{adjustMeal.cerealUnit}</td>
                  <td>{adjustMeal.riceUnit}</td>
                  <td>{adjustMeal.pastaUnit}</td>
                  <td>{adjustMeal.fruitUnit}</td>
                  <td>{adjustMeal.vegetableUnit}</td>
                  <td>{adjustMeal.fatUnit}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${adjustMeal.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${adjustMeal.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${adjustMeal.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="adjustApp.adjustMeal.home.notFound">No Adjust Meals found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ adjustMeal }: IRootState) => ({
  adjustMealList: adjustMeal.entities,
  loading: adjustMeal.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdjustMeal);

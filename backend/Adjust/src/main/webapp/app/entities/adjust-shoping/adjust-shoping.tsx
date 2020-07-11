import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './adjust-shoping.reducer';
import { IAdjustShoping } from 'app/shared/model/adjust-shoping.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdjustShopingProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const AdjustShoping = (props: IAdjustShopingProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { adjustShopingList, match, loading } = props;
  return (
    <div>
      <h2 id="adjust-shoping-heading">
        <Translate contentKey="adjustApp.adjustShoping.home.title">Adjust Shopings</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="adjustApp.adjustShoping.home.createLabel">Create new Adjust Shoping</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {adjustShopingList && adjustShopingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustShoping.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustShoping.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustShoping.token">Token</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustShoping.price">Price</Translate>
                </th>
                <th>
                  <Translate contentKey="adjustApp.adjustShoping.image">Image</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {adjustShopingList.map((adjustShoping, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${adjustShoping.id}`} color="link" size="sm">
                      {adjustShoping.id}
                    </Button>
                  </td>
                  <td>{adjustShoping.name}</td>
                  <td>{adjustShoping.description}</td>
                  <td>{adjustShoping.token}</td>
                  <td>{adjustShoping.price}</td>
                  <td>
                    {adjustShoping.image ? (
                      <div>
                        {adjustShoping.imageContentType ? (
                          <a onClick={openFile(adjustShoping.imageContentType, adjustShoping.image)}>
                            <img
                              src={`data:${adjustShoping.imageContentType};base64,${adjustShoping.image}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {adjustShoping.imageContentType}, {byteSize(adjustShoping.image)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${adjustShoping.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${adjustShoping.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${adjustShoping.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="adjustApp.adjustShoping.home.notFound">No Adjust Shopings found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ adjustShoping }: IRootState) => ({
  adjustShopingList: adjustShoping.entities,
  loading: adjustShoping.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdjustShoping);

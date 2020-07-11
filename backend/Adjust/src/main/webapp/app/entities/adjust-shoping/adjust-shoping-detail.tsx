import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './adjust-shoping.reducer';
import { IAdjustShoping } from 'app/shared/model/adjust-shoping.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdjustShopingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdjustShopingDetail = (props: IAdjustShopingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { adjustShopingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="adjustApp.adjustShoping.detail.title">AdjustShoping</Translate> [<b>{adjustShopingEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="adjustApp.adjustShoping.name">Name</Translate>
            </span>
          </dt>
          <dd>{adjustShopingEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="adjustApp.adjustShoping.description">Description</Translate>
            </span>
          </dt>
          <dd>{adjustShopingEntity.description}</dd>
          <dt>
            <span id="token">
              <Translate contentKey="adjustApp.adjustShoping.token">Token</Translate>
            </span>
          </dt>
          <dd>{adjustShopingEntity.token}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="adjustApp.adjustShoping.price">Price</Translate>
            </span>
          </dt>
          <dd>{adjustShopingEntity.price}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="adjustApp.adjustShoping.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {adjustShopingEntity.image ? (
              <div>
                {adjustShopingEntity.imageContentType ? (
                  <a onClick={openFile(adjustShopingEntity.imageContentType, adjustShopingEntity.image)}>
                    <img
                      src={`data:${adjustShopingEntity.imageContentType};base64,${adjustShopingEntity.image}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {adjustShopingEntity.imageContentType}, {byteSize(adjustShopingEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/adjust-shoping" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adjust-shoping/${adjustShopingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ adjustShoping }: IRootState) => ({
  adjustShopingEntity: adjustShoping.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdjustShopingDetail);

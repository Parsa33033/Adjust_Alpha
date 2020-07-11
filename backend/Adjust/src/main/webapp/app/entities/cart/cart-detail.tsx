import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cart.reducer';
import { ICart } from 'app/shared/model/cart.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICartDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CartDetail = (props: ICartDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { cartEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="adjustApp.cart.detail.title">Cart</Translate> [<b>{cartEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="username">
              <Translate contentKey="adjustApp.cart.username">Username</Translate>
            </span>
          </dt>
          <dd>{cartEntity.username}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="adjustApp.cart.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{cartEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="adjustApp.cart.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{cartEntity.lastName}</dd>
        </dl>
        <Button tag={Link} to="/cart" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cart/${cartEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ cart }: IRootState) => ({
  cartEntity: cart.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CartDetail);

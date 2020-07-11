import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAdjustClient } from 'app/shared/model/adjust-client.model';
import { getEntities as getAdjustClients } from 'app/entities/adjust-client/adjust-client.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './adjust-tutorial.reducer';
import { IAdjustTutorial } from 'app/shared/model/adjust-tutorial.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAdjustTutorialUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdjustTutorialUpdate = (props: IAdjustTutorialUpdateProps) => {
  const [clientId, setClientId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { adjustTutorialEntity, adjustClients, loading, updating } = props;

  const { thumbnail, thumbnailContentType, content, contentContentType } = adjustTutorialEntity;

  const handleClose = () => {
    props.history.push('/adjust-tutorial');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAdjustClients();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...adjustTutorialEntity,
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
          <h2 id="adjustApp.adjustTutorial.home.createOrEditLabel">
            <Translate contentKey="adjustApp.adjustTutorial.home.createOrEditLabel">Create or edit a AdjustTutorial</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : adjustTutorialEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="adjust-tutorial-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="adjust-tutorial-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="adjust-tutorial-title">
                  <Translate contentKey="adjustApp.adjustTutorial.title">Title</Translate>
                </Label>
                <AvField id="adjust-tutorial-title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="adjust-tutorial-description">
                  <Translate contentKey="adjustApp.adjustTutorial.description">Description</Translate>
                </Label>
                <AvField id="adjust-tutorial-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="thumbnailLabel" for="thumbnail">
                    <Translate contentKey="adjustApp.adjustTutorial.thumbnail">Thumbnail</Translate>
                  </Label>
                  <br />
                  {thumbnail ? (
                    <div>
                      {thumbnailContentType ? (
                        <a onClick={openFile(thumbnailContentType, thumbnail)}>
                          <img src={`data:${thumbnailContentType};base64,${thumbnail}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {thumbnailContentType}, {byteSize(thumbnail)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('thumbnail')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_thumbnail" type="file" onChange={onBlobChange(true, 'thumbnail')} accept="image/*" />
                  <AvInput type="hidden" name="thumbnail" value={thumbnail} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="tokenPriceLabel" for="adjust-tutorial-tokenPrice">
                  <Translate contentKey="adjustApp.adjustTutorial.tokenPrice">Token Price</Translate>
                </Label>
                <AvField id="adjust-tutorial-tokenPrice" type="string" className="form-control" name="tokenPrice" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="contentLabel" for="content">
                    <Translate contentKey="adjustApp.adjustTutorial.content">Content</Translate>
                  </Label>
                  <br />
                  {content ? (
                    <div>
                      {contentContentType ? (
                        <a onClick={openFile(contentContentType, content)}>
                          <Translate contentKey="entity.action.open">Open</Translate>
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {contentContentType}, {byteSize(content)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('content')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_content" type="file" onChange={onBlobChange(false, 'content')} />
                  <AvInput type="hidden" name="content" value={content} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label for="adjust-tutorial-client">
                  <Translate contentKey="adjustApp.adjustTutorial.client">Client</Translate>
                </Label>
                <AvInput id="adjust-tutorial-client" type="select" className="form-control" name="clientId">
                  <option value="" key="0" />
                  {adjustClients
                    ? adjustClients.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/adjust-tutorial" replace color="info">
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
  adjustClients: storeState.adjustClient.entities,
  adjustTutorialEntity: storeState.adjustTutorial.entity,
  loading: storeState.adjustTutorial.loading,
  updating: storeState.adjustTutorial.updating,
  updateSuccess: storeState.adjustTutorial.updateSuccess,
});

const mapDispatchToProps = {
  getAdjustClients,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdjustTutorialUpdate);

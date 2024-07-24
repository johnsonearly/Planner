import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-user.reducer';

export const AppUserDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appUserEntity = useAppSelector(state => state.appUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appUserDetailsHeading">
          <Translate contentKey="plannerBotApp.appUser.detail.title">AppUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="plannerBotApp.appUser.name">Name</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.name}</dd>
          <dt>
            <span id="age">
              <Translate contentKey="plannerBotApp.appUser.age">Age</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.age}</dd>
          <dt>
            <span id="appUserId">
              <Translate contentKey="plannerBotApp.appUser.appUserId">App User Id</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.appUserId}</dd>
          <dt>
            <span id="chronotype">
              <Translate contentKey="plannerBotApp.appUser.chronotype">Chronotype</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.chronotype}</dd>
          <dt>
            <span id="readingType">
              <Translate contentKey="plannerBotApp.appUser.readingType">Reading Type</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.readingType}</dd>
          <dt>
            <span id="attentionSpan">
              <Translate contentKey="plannerBotApp.appUser.attentionSpan">Attention Span</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.attentionSpan}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="plannerBotApp.appUser.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.gender}</dd>
          <dt>
            <span id="readingStrategy">
              <Translate contentKey="plannerBotApp.appUser.readingStrategy">Reading Strategy</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.readingStrategy}</dd>
        </dl>
        <Button tag={Link} to="/app-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-user/${appUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppUserDetail;

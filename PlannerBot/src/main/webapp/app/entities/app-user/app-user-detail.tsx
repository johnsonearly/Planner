import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
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
        <h2 data-cy="appUserDetailsHeading">App User</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{appUserEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{appUserEntity.name}</dd>
          <dt>
            <span id="age">Age</span>
          </dt>
          <dd>{appUserEntity.age}</dd>
          <dt>
            <span id="appUserId">App User Id</span>
          </dt>
          <dd>{appUserEntity.appUserId}</dd>
          <dt>
            <span id="chronotype">Chronotype</span>
          </dt>
          <dd>{appUserEntity.chronotype}</dd>
          <dt>
            <span id="readingType">Reading Type</span>
          </dt>
          <dd>{appUserEntity.readingType}</dd>
          <dt>
            <span id="attentionSpan">Attention Span</span>
          </dt>
          <dd>{appUserEntity.attentionSpan}</dd>
          <dt>
            <span id="gender">Gender</span>
          </dt>
          <dd>{appUserEntity.gender}</dd>
        </dl>
        <Button tag={Link} to="/app-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-user/${appUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppUserDetail;

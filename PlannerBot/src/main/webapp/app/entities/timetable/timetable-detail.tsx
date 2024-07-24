import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './timetable.reducer';

export const TimetableDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const timetableEntity = useAppSelector(state => state.timetable.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="timetableDetailsHeading">
          <Translate contentKey="plannerBotApp.timetable.detail.title">Timetable</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{timetableEntity.id}</dd>
          <dt>
            <span id="appUserId">
              <Translate contentKey="plannerBotApp.timetable.appUserId">App User Id</Translate>
            </span>
          </dt>
          <dd>{timetableEntity.appUserId}</dd>
          <dt>
            <span id="dayOfWeek">
              <Translate contentKey="plannerBotApp.timetable.dayOfWeek">Day Of Week</Translate>
            </span>
          </dt>
          <dd>{timetableEntity.dayOfWeek}</dd>
          <dt>
            <span id="dateOfActivity">
              <Translate contentKey="plannerBotApp.timetable.dateOfActivity">Date Of Activity</Translate>
            </span>
          </dt>
          <dd>
            {timetableEntity.dateOfActivity ? (
              <TextFormat value={timetableEntity.dateOfActivity} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="plannerBotApp.timetable.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {timetableEntity.startTime ? <TextFormat value={timetableEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="plannerBotApp.timetable.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>{timetableEntity.endTime ? <TextFormat value={timetableEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="activity">
              <Translate contentKey="plannerBotApp.timetable.activity">Activity</Translate>
            </span>
          </dt>
          <dd>{timetableEntity.activity}</dd>
          <dt>
            <span id="isDone">
              <Translate contentKey="plannerBotApp.timetable.isDone">Is Done</Translate>
            </span>
          </dt>
          <dd>{timetableEntity.isDone ? 'true' : 'false'}</dd>
          <dt>
            <span id="levelOfImportance">
              <Translate contentKey="plannerBotApp.timetable.levelOfImportance">Level Of Importance</Translate>
            </span>
          </dt>
          <dd>{timetableEntity.levelOfImportance}</dd>
        </dl>
        <Button tag={Link} to="/timetable" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/timetable/${timetableEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TimetableDetail;

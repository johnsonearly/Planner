import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
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
        <h2 data-cy="timetableDetailsHeading">Timetable</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{timetableEntity.id}</dd>
          <dt>
            <span id="appUserId">App User Id</span>
          </dt>
          <dd>{timetableEntity.appUserId}</dd>
          <dt>
            <span id="dayOfWeek">Day Of Week</span>
          </dt>
          <dd>{timetableEntity.dayOfWeek}</dd>
          <dt>
            <span id="dateOfActivity">Date Of Activity</span>
          </dt>
          <dd>
            {timetableEntity.dateOfActivity ? (
              <TextFormat value={timetableEntity.dateOfActivity} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="startTime">Start Time</span>
          </dt>
          <dd>
            {timetableEntity.startTime ? <TextFormat value={timetableEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endTime">End Time</span>
          </dt>
          <dd>{timetableEntity.endTime ? <TextFormat value={timetableEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="activity">Activity</span>
          </dt>
          <dd>{timetableEntity.activity}</dd>
          <dt>
            <span id="isDone">Is Done</span>
          </dt>
          <dd>{timetableEntity.isDone ? 'true' : 'false'}</dd>
          <dt>
            <span id="levelOfImportance">Level Of Importance</span>
          </dt>
          <dd>{timetableEntity.levelOfImportance}</dd>
        </dl>
        <Button tag={Link} to="/timetable" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/timetable/${timetableEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TimetableDetail;

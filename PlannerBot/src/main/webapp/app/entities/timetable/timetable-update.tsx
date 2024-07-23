import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITimetable } from 'app/shared/model/timetable.model';
import { Importance } from 'app/shared/model/enumerations/importance.model';
import { getEntity, updateEntity, createEntity, reset } from './timetable.reducer';

export const TimetableUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const timetableEntity = useAppSelector(state => state.timetable.entity);
  const loading = useAppSelector(state => state.timetable.loading);
  const updating = useAppSelector(state => state.timetable.updating);
  const updateSuccess = useAppSelector(state => state.timetable.updateSuccess);
  const importanceValues = Object.keys(Importance);

  const handleClose = () => {
    navigate('/timetable' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.appUserId !== undefined && typeof values.appUserId !== 'number') {
      values.appUserId = Number(values.appUserId);
    }
    values.startTime = convertDateTimeToServer(values.startTime);
    values.endTime = convertDateTimeToServer(values.endTime);

    const entity = {
      ...timetableEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          startTime: displayDefaultDateTime(),
          endTime: displayDefaultDateTime(),
        }
      : {
          levelOfImportance: 'LOW',
          ...timetableEntity,
          startTime: convertDateTimeFromServer(timetableEntity.startTime),
          endTime: convertDateTimeFromServer(timetableEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="plannerBotApp.timetable.home.createOrEditLabel" data-cy="TimetableCreateUpdateHeading">
            Create or edit a Timetable
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="timetable-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="App User Id" id="timetable-appUserId" name="appUserId" data-cy="appUserId" type="text" />
              <ValidatedField label="Day Of Week" id="timetable-dayOfWeek" name="dayOfWeek" data-cy="dayOfWeek" type="text" />
              <ValidatedField
                label="Date Of Activity"
                id="timetable-dateOfActivity"
                name="dateOfActivity"
                data-cy="dateOfActivity"
                type="date"
              />
              <ValidatedField
                label="Start Time"
                id="timetable-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="End Time"
                id="timetable-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Activity" id="timetable-activity" name="activity" data-cy="activity" type="text" />
              <ValidatedField label="Is Done" id="timetable-isDone" name="isDone" data-cy="isDone" check type="checkbox" />
              <ValidatedField
                label="Level Of Importance"
                id="timetable-levelOfImportance"
                name="levelOfImportance"
                data-cy="levelOfImportance"
                type="select"
              >
                {importanceValues.map(importance => (
                  <option value={importance} key={importance}>
                    {importance}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/timetable" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TimetableUpdate;

import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppUser } from 'app/shared/model/app-user.model';
import { Chronotype } from 'app/shared/model/enumerations/chronotype.model';
import { ReadingType } from 'app/shared/model/enumerations/reading-type.model';
import { AttentionSpan } from 'app/shared/model/enumerations/attention-span.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { ReadingStrategy } from 'app/shared/model/enumerations/reading-strategy.model';
import { getEntity, updateEntity, createEntity, reset } from './app-user.reducer';

export const AppUserUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appUserEntity = useAppSelector(state => state.appUser.entity);
  const loading = useAppSelector(state => state.appUser.loading);
  const updating = useAppSelector(state => state.appUser.updating);
  const updateSuccess = useAppSelector(state => state.appUser.updateSuccess);
  const chronotypeValues = Object.keys(Chronotype);
  const readingTypeValues = Object.keys(ReadingType);
  const attentionSpanValues = Object.keys(AttentionSpan);
  const genderValues = Object.keys(Gender);
  const readingStrategyValues = Object.keys(ReadingStrategy);

  const handleClose = () => {
    navigate('/app-user' + location.search);
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
    if (values.age !== undefined && typeof values.age !== 'number') {
      values.age = Number(values.age);
    }
    if (values.appUserId !== undefined && typeof values.appUserId !== 'number') {
      values.appUserId = Number(values.appUserId);
    }

    const entity = {
      ...appUserEntity,
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
      ? {}
      : {
          chronotype: 'MORNING',
          readingType: 'INTENSIVE',
          attentionSpan: 'SHORT',
          gender: 'MALE',
          readingStrategy: 'SPACED_REPETITION',
          ...appUserEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="plannerBotApp.appUser.home.createOrEditLabel" data-cy="AppUserCreateUpdateHeading">
            Create or edit a App User
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="app-user-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Name" id="app-user-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Age" id="app-user-age" name="age" data-cy="age" type="text" />
              <ValidatedField label="App User Id" id="app-user-appUserId" name="appUserId" data-cy="appUserId" type="text" />
              <ValidatedField label="Chronotype" id="app-user-chronotype" name="chronotype" data-cy="chronotype" type="select">
                {chronotypeValues.map(chronotype => (
                  <option value={chronotype} key={chronotype}>
                    {chronotype}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Reading Type" id="app-user-readingType" name="readingType" data-cy="readingType" type="select">
                {readingTypeValues.map(readingType => (
                  <option value={readingType} key={readingType}>
                    {readingType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Attention Span" id="app-user-attentionSpan" name="attentionSpan" data-cy="attentionSpan" type="select">
                {attentionSpanValues.map(attentionSpan => (
                  <option value={attentionSpan} key={attentionSpan}>
                    {attentionSpan}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Gender" id="app-user-gender" name="gender" data-cy="gender" type="select">
                {genderValues.map(gender => (
                  <option value={gender} key={gender}>
                    {gender}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Reading Strategy"
                id="app-user-readingStrategy"
                name="readingStrategy"
                data-cy="readingStrategy"
                type="select"
              >
                {readingStrategyValues.map(readingStrategy => (
                  <option value={readingStrategy} key={readingStrategy}>
                    {readingStrategy}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-user" replace color="info">
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

export default AppUserUpdate;

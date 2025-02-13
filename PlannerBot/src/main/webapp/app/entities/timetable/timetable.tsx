import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './timetable.reducer';

export const Timetable = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const timetableList = useAppSelector(state => state.timetable.entities);
  const loading = useAppSelector(state => state.timetable.loading);
  const totalItems = useAppSelector(state => state.timetable.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="timetable-heading" data-cy="TimetableHeading">
        <Translate contentKey="plannerBotApp.timetable.home.title">Timetables</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="plannerBotApp.timetable.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/timetable/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="plannerBotApp.timetable.home.createLabel">Create new Timetable</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {timetableList && timetableList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="plannerBotApp.timetable.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('appUserId')}>
                  <Translate contentKey="plannerBotApp.timetable.appUserId">App User Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('appUserId')} />
                </th>
                <th className="hand" onClick={sort('dayOfWeek')}>
                  <Translate contentKey="plannerBotApp.timetable.dayOfWeek">Day Of Week</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dayOfWeek')} />
                </th>
                <th className="hand" onClick={sort('dateOfActivity')}>
                  <Translate contentKey="plannerBotApp.timetable.dateOfActivity">Date Of Activity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dateOfActivity')} />
                </th>
                <th className="hand" onClick={sort('startTime')}>
                  <Translate contentKey="plannerBotApp.timetable.startTime">Start Time</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startTime')} />
                </th>
                <th className="hand" onClick={sort('endTime')}>
                  <Translate contentKey="plannerBotApp.timetable.endTime">End Time</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('endTime')} />
                </th>
                <th className="hand" onClick={sort('activity')}>
                  <Translate contentKey="plannerBotApp.timetable.activity">Activity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('activity')} />
                </th>
                <th className="hand" onClick={sort('isDone')}>
                  <Translate contentKey="plannerBotApp.timetable.isDone">Is Done</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isDone')} />
                </th>
                <th className="hand" onClick={sort('levelOfImportance')}>
                  <Translate contentKey="plannerBotApp.timetable.levelOfImportance">Level Of Importance</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('levelOfImportance')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {timetableList.map((timetable, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/timetable/${timetable.id}`} color="link" size="sm">
                      {timetable.id}
                    </Button>
                  </td>
                  <td>{timetable.appUserId}</td>
                  <td>{timetable.dayOfWeek}</td>
                  <td>
                    {timetable.dateOfActivity ? (
                      <TextFormat type="date" value={timetable.dateOfActivity} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{timetable.startTime ? <TextFormat type="date" value={timetable.startTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{timetable.endTime ? <TextFormat type="date" value={timetable.endTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{timetable.activity}</td>
                  <td>{timetable.isDone ? 'true' : 'false'}</td>
                  <td>
                    <Translate contentKey={`plannerBotApp.Importance.${timetable.levelOfImportance}`} />
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/timetable/${timetable.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/timetable/${timetable.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/timetable/${timetable.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="plannerBotApp.timetable.home.notFound">No Timetables found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={timetableList && timetableList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Timetable;

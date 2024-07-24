import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './app-user.reducer';

export const AppUser = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const appUserList = useAppSelector(state => state.appUser.entities);
  const loading = useAppSelector(state => state.appUser.loading);
  const totalItems = useAppSelector(state => state.appUser.totalItems);

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
      <h2 id="app-user-heading" data-cy="AppUserHeading">
        <Translate contentKey="plannerBotApp.appUser.home.title">App Users</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="plannerBotApp.appUser.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/app-user/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="plannerBotApp.appUser.home.createLabel">Create new App User</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {appUserList && appUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="plannerBotApp.appUser.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="plannerBotApp.appUser.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('age')}>
                  <Translate contentKey="plannerBotApp.appUser.age">Age</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('age')} />
                </th>
                <th className="hand" onClick={sort('appUserId')}>
                  <Translate contentKey="plannerBotApp.appUser.appUserId">App User Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('appUserId')} />
                </th>
                <th className="hand" onClick={sort('chronotype')}>
                  <Translate contentKey="plannerBotApp.appUser.chronotype">Chronotype</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('chronotype')} />
                </th>
                <th className="hand" onClick={sort('readingType')}>
                  <Translate contentKey="plannerBotApp.appUser.readingType">Reading Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('readingType')} />
                </th>
                <th className="hand" onClick={sort('attentionSpan')}>
                  <Translate contentKey="plannerBotApp.appUser.attentionSpan">Attention Span</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('attentionSpan')} />
                </th>
                <th className="hand" onClick={sort('gender')}>
                  <Translate contentKey="plannerBotApp.appUser.gender">Gender</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('gender')} />
                </th>
                <th className="hand" onClick={sort('readingStrategy')}>
                  <Translate contentKey="plannerBotApp.appUser.readingStrategy">Reading Strategy</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('readingStrategy')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {appUserList.map((appUser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/app-user/${appUser.id}`} color="link" size="sm">
                      {appUser.id}
                    </Button>
                  </td>
                  <td>{appUser.name}</td>
                  <td>{appUser.age}</td>
                  <td>{appUser.appUserId}</td>
                  <td>
                    <Translate contentKey={`plannerBotApp.Chronotype.${appUser.chronotype}`} />
                  </td>
                  <td>
                    <Translate contentKey={`plannerBotApp.ReadingType.${appUser.readingType}`} />
                  </td>
                  <td>
                    <Translate contentKey={`plannerBotApp.AttentionSpan.${appUser.attentionSpan}`} />
                  </td>
                  <td>
                    <Translate contentKey={`plannerBotApp.Gender.${appUser.gender}`} />
                  </td>
                  <td>
                    <Translate contentKey={`plannerBotApp.ReadingStrategy.${appUser.readingStrategy}`} />
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/app-user/${appUser.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/app-user/${appUser.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (window.location.href = `/app-user/${appUser.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="plannerBotApp.appUser.home.notFound">No App Users found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={appUserList && appUserList.length > 0 ? '' : 'd-none'}>
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

export default AppUser;

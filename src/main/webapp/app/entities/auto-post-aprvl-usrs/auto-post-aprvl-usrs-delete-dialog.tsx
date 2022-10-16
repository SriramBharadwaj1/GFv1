import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './auto-post-aprvl-usrs.reducer';

export const AutoPostAprvlUsrsDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const autoPostAprvlUsrsEntity = useAppSelector(state => state.autoPostAprvlUsrs.entity);
  const updateSuccess = useAppSelector(state => state.autoPostAprvlUsrs.updateSuccess);

  const handleClose = () => {
    navigate('/auto-post-aprvl-usrs' + location.search);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(autoPostAprvlUsrsEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="autoPostAprvlUsrsDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="groupfaceApp.autoPostAprvlUsrs.delete.question">
        <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.delete.question" interpolate={{ id: autoPostAprvlUsrsEntity.id }}>
          Are you sure you want to delete this AutoPostAprvlUsrs?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="lap-confirm-delete-autoPostAprvlUsrs" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default AutoPostAprvlUsrsDeleteDialog;

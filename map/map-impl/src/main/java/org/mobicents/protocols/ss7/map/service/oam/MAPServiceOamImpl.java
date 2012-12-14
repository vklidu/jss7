/*
 * TeleStax, Open Source Cloud Communications  Copyright 2012.
 * and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.protocols.ss7.map.service.oam;

import org.apache.log4j.Logger;
import org.mobicents.protocols.ss7.map.MAPDialogImpl;
import org.mobicents.protocols.ss7.map.MAPProviderImpl;
import org.mobicents.protocols.ss7.map.MAPServiceBaseImpl;
import org.mobicents.protocols.ss7.map.api.MAPApplicationContext;
import org.mobicents.protocols.ss7.map.api.MAPDialog;
import org.mobicents.protocols.ss7.map.api.MAPException;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentException;
import org.mobicents.protocols.ss7.map.api.dialog.ServingCheckData;
import org.mobicents.protocols.ss7.map.api.dialog.ServingCheckResult;
import org.mobicents.protocols.ss7.map.api.primitives.AddressString;
import org.mobicents.protocols.ss7.map.api.service.oam.MAPDialogOam;
import org.mobicents.protocols.ss7.map.api.service.oam.MAPServiceOam;
import org.mobicents.protocols.ss7.map.api.service.oam.MAPServiceOamListener;
import org.mobicents.protocols.ss7.map.dialog.ServingCheckDataImpl;
import org.mobicents.protocols.ss7.sccp.parameter.SccpAddress;
import org.mobicents.protocols.ss7.tcap.api.tc.dialog.Dialog;
import org.mobicents.protocols.ss7.tcap.asn.comp.ComponentType;
import org.mobicents.protocols.ss7.tcap.asn.comp.Invoke;
import org.mobicents.protocols.ss7.tcap.asn.comp.OperationCode;
import org.mobicents.protocols.ss7.tcap.asn.comp.Parameter;

/**
 * 
 * @author sergey vetyutnev
 * 
 */
public class MAPServiceOamImpl extends MAPServiceBaseImpl implements MAPServiceOam {

	protected Logger loger = Logger.getLogger(MAPServiceOamImpl.class);

	public MAPServiceOamImpl(MAPProviderImpl mapProviderImpl) {
		super(mapProviderImpl);
	}

	/*
	 * Creating a new outgoing MAP OAM dialog and adding it to the
	 * MAPProvider.dialog collection
	 * 
	 */
	public MAPDialogOam createNewDialog(MAPApplicationContext appCntx, SccpAddress origAddress, AddressString origReference, SccpAddress destAddress,
			AddressString destReference) throws MAPException {

		// We cannot create a dialog if the service is not activated
		if (!this.isActivated())
			throw new MAPException(
					"Cannot create MAPDialogOam because MAPServiceOam is not activated");

		Dialog tcapDialog = this.createNewTCAPDialog(origAddress, destAddress);
		MAPDialogOamImpl dialog = new MAPDialogOamImpl(appCntx, tcapDialog, this.mapProviderImpl, this, origReference, destReference);

		this.putMAPDialogIntoCollection(dialog);

		return dialog;
	}

	protected MAPDialogImpl createNewDialogIncoming(MAPApplicationContext appCntx, Dialog tcapDialog) {
		return new MAPDialogOamImpl(appCntx, tcapDialog, this.mapProviderImpl, this, null, null);
	}

	public void addMAPServiceListener(MAPServiceOamListener mapServiceListener) {
		super.addMAPServiceListener(mapServiceListener);
	}

	public void removeMAPServiceListener(MAPServiceOamListener mapServiceListener) {
		super.removeMAPServiceListener(mapServiceListener);
	}

	public ServingCheckData isServingService(MAPApplicationContext dialogApplicationContext) {
		// TODO implement it
		
		return new ServingCheckDataImpl(ServingCheckResult.AC_NotServing);
	}

	public void processComponent(ComponentType compType, OperationCode oc, Parameter parameter, MAPDialog mapDialog, Long invokeId, Long linkedId,
			Invoke linkedInvoke) throws MAPParsingComponentException {
		// TODO implement it
		
	}
}

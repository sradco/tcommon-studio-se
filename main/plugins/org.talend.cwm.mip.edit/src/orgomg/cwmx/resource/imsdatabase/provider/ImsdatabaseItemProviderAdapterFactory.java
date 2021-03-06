/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package orgomg.cwmx.resource.imsdatabase.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import orgomg.cwmx.resource.imsdatabase.util.ImsdatabaseAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ImsdatabaseItemProviderAdapterFactory extends ImsdatabaseAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
    /**
     * This keeps track of the root adapter factory that delegates to this adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ComposedAdapterFactory parentAdapterFactory;

    /**
     * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IChangeNotifier changeNotifier = new ChangeNotifier();

    /**
     * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Collection<Object> supportedTypes = new ArrayList<Object>();

    /**
     * This constructs an instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ImsdatabaseItemProviderAdapterFactory() {
        supportedTypes.add(IEditingDomainItemProvider.class);
        supportedTypes.add(IStructuredItemContentProvider.class);
        supportedTypes.add(ITreeItemContentProvider.class);
        supportedTypes.add(IItemLabelProvider.class);
        supportedTypes.add(IItemPropertySource.class);
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.DBD} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DBDItemProvider dbdItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.DBD}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createDBDAdapter() {
        if (dbdItemProvider == null) {
            dbdItemProvider = new DBDItemProvider(this);
        }

        return dbdItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.PSB} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PSBItemProvider psbItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.PSB}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createPSBAdapter() {
        if (psbItemProvider == null) {
            psbItemProvider = new PSBItemProvider(this);
        }

        return psbItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.PCB} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PCBItemProvider pcbItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.PCB}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createPCBAdapter() {
        if (pcbItemProvider == null) {
            pcbItemProvider = new PCBItemProvider(this);
        }

        return pcbItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.Segment} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SegmentItemProvider segmentItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.Segment}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createSegmentAdapter() {
        if (segmentItemProvider == null) {
            segmentItemProvider = new SegmentItemProvider(this);
        }

        return segmentItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.SegmentComplex} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SegmentComplexItemProvider segmentComplexItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.SegmentComplex}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createSegmentComplexAdapter() {
        if (segmentComplexItemProvider == null) {
            segmentComplexItemProvider = new SegmentComplexItemProvider(this);
        }

        return segmentComplexItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.SegmentLogical} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SegmentLogicalItemProvider segmentLogicalItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.SegmentLogical}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createSegmentLogicalAdapter() {
        if (segmentLogicalItemProvider == null) {
            segmentLogicalItemProvider = new SegmentLogicalItemProvider(this);
        }

        return segmentLogicalItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.Field} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FieldItemProvider fieldItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.Field}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createFieldAdapter() {
        if (fieldItemProvider == null) {
            fieldItemProvider = new FieldItemProvider(this);
        }

        return fieldItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.Dataset} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DatasetItemProvider datasetItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.Dataset}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createDatasetAdapter() {
        if (datasetItemProvider == null) {
            datasetItemProvider = new DatasetItemProvider(this);
        }

        return datasetItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.SenSegment} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SenSegmentItemProvider senSegmentItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.SenSegment}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createSenSegmentAdapter() {
        if (senSegmentItemProvider == null) {
            senSegmentItemProvider = new SenSegmentItemProvider(this);
        }

        return senSegmentItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.SenField} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SenFieldItemProvider senFieldItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.SenField}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createSenFieldAdapter() {
        if (senFieldItemProvider == null) {
            senFieldItemProvider = new SenFieldItemProvider(this);
        }

        return senFieldItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.ACBLIB} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ACBLIBItemProvider acblibItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.ACBLIB}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createACBLIBAdapter() {
        if (acblibItemProvider == null) {
            acblibItemProvider = new ACBLIBItemProvider(this);
        }

        return acblibItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.AccessMethod} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AccessMethodItemProvider accessMethodItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.AccessMethod}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createAccessMethodAdapter() {
        if (accessMethodItemProvider == null) {
            accessMethodItemProvider = new AccessMethodItemProvider(this);
        }

        return accessMethodItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.INDEX} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected INDEXItemProvider indexItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.INDEX}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createINDEXAdapter() {
        if (indexItemProvider == null) {
            indexItemProvider = new INDEXItemProvider(this);
        }

        return indexItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.HIDAM} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected HIDAMItemProvider hidamItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.HIDAM}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createHIDAMAdapter() {
        if (hidamItemProvider == null) {
            hidamItemProvider = new HIDAMItemProvider(this);
        }

        return hidamItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.DEDB} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DEDBItemProvider dedbItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.DEDB}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createDEDBAdapter() {
        if (dedbItemProvider == null) {
            dedbItemProvider = new DEDBItemProvider(this);
        }

        return dedbItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.HDAM} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected HDAMItemProvider hdamItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.HDAM}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createHDAMAdapter() {
        if (hdamItemProvider == null) {
            hdamItemProvider = new HDAMItemProvider(this);
        }

        return hdamItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.MSDB} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MSDBItemProvider msdbItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.MSDB}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createMSDBAdapter() {
        if (msdbItemProvider == null) {
            msdbItemProvider = new MSDBItemProvider(this);
        }

        return msdbItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.SecondaryIndex} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SecondaryIndexItemProvider secondaryIndexItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.SecondaryIndex}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createSecondaryIndexAdapter() {
        if (secondaryIndexItemProvider == null) {
            secondaryIndexItemProvider = new SecondaryIndexItemProvider(this);
        }

        return secondaryIndexItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.Exit} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ExitItemProvider exitItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.Exit}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createExitAdapter() {
        if (exitItemProvider == null) {
            exitItemProvider = new ExitItemProvider(this);
        }

        return exitItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.LCHILD} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected LCHILDItemProvider lchildItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.LCHILD}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createLCHILDAdapter() {
        if (lchildItemProvider == null) {
            lchildItemProvider = new LCHILDItemProvider(this);
        }

        return lchildItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.PSBLib} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PSBLibItemProvider psbLibItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.PSBLib}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createPSBLibAdapter() {
        if (psbLibItemProvider == null) {
            psbLibItemProvider = new PSBLibItemProvider(this);
        }

        return psbLibItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.imsdatabase.DBDLib} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DBDLibItemProvider dbdLibItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.imsdatabase.DBDLib}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createDBDLibAdapter() {
        if (dbdLibItemProvider == null) {
            dbdLibItemProvider = new DBDLibItemProvider(this);
        }

        return dbdLibItemProvider;
    }

    /**
     * This returns the root adapter factory that contains this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ComposeableAdapterFactory getRootAdapterFactory() {
        return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
    }

    /**
     * This sets the composed adapter factory that contains this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
        this.parentAdapterFactory = parentAdapterFactory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object type) {
        return supportedTypes.contains(type) || super.isFactoryForType(type);
    }

    /**
     * This implementation substitutes the factory itself as the key for the adapter.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter adapt(Notifier notifier, Object type) {
        return super.adapt(notifier, this);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object adapt(Object object, Object type) {
        if (isFactoryForType(type)) {
            Object adapter = super.adapt(object, type);
            if (!(type instanceof Class) || (((Class<?>)type).isInstance(adapter))) {
                return adapter;
            }
        }

        return null;
    }

    /**
     * This adds a listener.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void addListener(INotifyChangedListener notifyChangedListener) {
        changeNotifier.addListener(notifyChangedListener);
    }

    /**
     * This removes a listener.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void removeListener(INotifyChangedListener notifyChangedListener) {
        changeNotifier.removeListener(notifyChangedListener);
    }

    /**
     * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void fireNotifyChanged(Notification notification) {
        changeNotifier.fireNotifyChanged(notification);

        if (parentAdapterFactory != null) {
            parentAdapterFactory.fireNotifyChanged(notification);
        }
    }

    /**
     * This disposes all of the item providers created by this factory. 
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void dispose() {
        if (dbdItemProvider != null) dbdItemProvider.dispose();
        if (psbItemProvider != null) psbItemProvider.dispose();
        if (pcbItemProvider != null) pcbItemProvider.dispose();
        if (segmentItemProvider != null) segmentItemProvider.dispose();
        if (segmentComplexItemProvider != null) segmentComplexItemProvider.dispose();
        if (segmentLogicalItemProvider != null) segmentLogicalItemProvider.dispose();
        if (fieldItemProvider != null) fieldItemProvider.dispose();
        if (datasetItemProvider != null) datasetItemProvider.dispose();
        if (senSegmentItemProvider != null) senSegmentItemProvider.dispose();
        if (senFieldItemProvider != null) senFieldItemProvider.dispose();
        if (acblibItemProvider != null) acblibItemProvider.dispose();
        if (accessMethodItemProvider != null) accessMethodItemProvider.dispose();
        if (indexItemProvider != null) indexItemProvider.dispose();
        if (hidamItemProvider != null) hidamItemProvider.dispose();
        if (dedbItemProvider != null) dedbItemProvider.dispose();
        if (hdamItemProvider != null) hdamItemProvider.dispose();
        if (msdbItemProvider != null) msdbItemProvider.dispose();
        if (secondaryIndexItemProvider != null) secondaryIndexItemProvider.dispose();
        if (exitItemProvider != null) exitItemProvider.dispose();
        if (lchildItemProvider != null) lchildItemProvider.dispose();
        if (psbLibItemProvider != null) psbLibItemProvider.dispose();
        if (dbdLibItemProvider != null) dbdLibItemProvider.dispose();
    }

}
